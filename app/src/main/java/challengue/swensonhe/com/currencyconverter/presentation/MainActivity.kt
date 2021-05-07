package challengue.swensonhe.com.currencyconverter.presentation


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import challengue.swensonhe.com.currencyconverter.CurrencyApplication
import challengue.swensonhe.com.currencyconverter.R
import challengue.swensonhe.com.currencyconverter.model.Rate
import challengue.swensonhe.com.currencyconverter.model.RateResponse
import challengue.swensonhe.com.currencyconverter.utils.ConnectionLiveData
import challengue.swensonhe.com.currencyconverter.viewModels.CurrencyRateViewModel
import kotlinx.android.synthetic.main.activity_change_rate.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity() : AppCompatActivity() {
    private var rates = mutableListOf<Rate>()
    lateinit var latestRateViewModel: CurrencyRateViewModel
    lateinit var rateAdapter: CurrencyRateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        latestRateViewModel = CurrencyApplication.injectCurrencyViewModel()

        //Observe for internet connection
        ConnectionLiveData
            .observe(this, Observer {
                if (it.isConnected) {
                    Log.e("On connect","Connect")
                    no_internet.visibility = View.GONE
                    CurrencyRateRecyclerView.visibility = View.VISIBLE
                    base_tv.visibility = View.VISIBLE
                    latestRateViewModel.getLatestRate()
                } else {
                    Log.e("On connect","Lost")
                    no_internet.visibility = View.VISIBLE
                    CurrencyRateRecyclerView.visibility = View.GONE
                    base_tv.visibility = View.GONE
                    Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show()
                }

            })


        rateAdapter = CurrencyRateAdapter { position, item ->
            onItemClick(position, item)
        }
        initCurrencyRecyclerView()
        latestRateViewModel.ObserveOnLatestCurrencyRate(this, Observer {
            HandleRateState(it)


        })


    }

    private fun onItemClick(position: Int, item: Rate) {
        ChangeRateActivity.getStartIntentForConverter(this, item)

    }

    private fun HandleRateState(rateResponse: RateResponse) {

        base_tv.text = resources.getString(R.string.base_currency).plus("  :  ").plus(rateResponse.base.toString())

        if (rateResponse.rates?.entries?.size != 0) {
            rateResponse.rates?.entries?.forEach { it ->
                Rate(it.key, it.value)
                rates.add(Rate(it.key, it.value))

            }
        }
        rateAdapter.items.addAll(rates as ArrayList<Rate>)
        rateAdapter.notifyDataSetChanged()


    }

    private fun initCurrencyRecyclerView() {
        CurrencyRateRecyclerView.layoutManager = LinearLayoutManager(this)

        CurrencyRateRecyclerView.adapter = rateAdapter
        CurrencyRateRecyclerView.isNestedScrollingEnabled = false

        //CurrencyRateRecyclerView.setHasFixedSize(true)
    }

}
