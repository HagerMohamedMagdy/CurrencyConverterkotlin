package challengue.swensonhe.com.currencyconverter.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData


object ConnectionLiveData : LiveData<ConnectionLiveData.ConnectionModel>() {


    fun init(context: Application) {
        ConnectionLiveData.context = context
    }

    private lateinit var context: Application

    private val LOG_TAG: String = ConnectionLiveData::class.java.simpleName


    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager.registerDefaultNetworkCallback(
                getConnectivityManagerCallback()
            )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> lollipopNetworkAvailableRequest()
            else -> {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    context.registerReceiver(networkReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")) // android.net.ConnectivityManager.CONNECTIVITY_ACTION
                }
            }
        }
        updateConnection()
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(
                connectivityManagerCallback
            )
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }

    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkAvailableRequest() {
        val builder = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        connectivityManager.registerNetworkCallback(builder.build(),
            getConnectivityManagerCallback()
        )
    }

    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network?) {
                    updateConnection()
                }

                override fun onLost(network: Network?) {
                    updateConnection()
                }
            }
            return connectivityManagerCallback
        } else {
            throw IllegalAccessError("Should not happened")
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateConnection()
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        val isConnected = activeNetwork?.isConnected == true
        val connectionType = getConnectionType()
        Log.d(LOG_TAG, "isConnected: {$isConnected} and connection type: {${connectionType.name}}")
        postValue(
            ConnectionModel(
                isConnected = isConnected,
                connectionType = connectionType
            )
        )
    }

    @SuppressLint("MissingPermission")
    fun getConnectionType(): ConnectionType {
        var result =
            ConnectionType.UNKNOWN // Returns connection type. 0: none; 1: mobile data; 2: wifi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.run {
                connectivityManager.getNetworkCapabilities(
                    connectivityManager.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = ConnectionType.WIFI
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = ConnectionType.DATA
                    }
                }
            }
        } else {
            connectivityManager?.run {
                connectivityManager.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = ConnectionType.WIFI
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = ConnectionType.DATA
                    }
                }
            }
        }
        return result
    }

    data class ConnectionModel(val isConnected: Boolean, val connectionType: ConnectionType)
    enum class ConnectionType {
        WIFI, DATA, UNKNOWN
    }
}
