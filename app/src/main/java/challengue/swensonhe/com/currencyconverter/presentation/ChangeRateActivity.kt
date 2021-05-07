package challengue.swensonhe.com.currencyconverter.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import challengue.swensonhe.com.currencyconverter.R
import challengue.swensonhe.com.currencyconverter.model.Rate
import de.tobiasschuerg.money.Currency
import de.tobiasschuerg.money.Money
import kotlinx.android.synthetic.main.activity_change_rate.*
import vas.com.currencyconverter.CurrencyConverter
import java.util.*

class ChangeRateActivity : AppCompatActivity() {
lateinit var rateItem:Rate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_rate)
        base_et.hideKeyboard()
       // base_et.requestFocus()
        initViews()



        if (intent.hasExtra(RATE_ITEM_ARG)) {
            rateItem = intent.getParcelableExtra(RATE_ITEM_ARG)
            select_name.text=rateItem.name
            selected_et.text=rateItem.value.toString()


    }




        base_et.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                Log.e("Done","Clicked")
                val input = base_et?.text.toString().trim()
                if (input.isNullOrBlank()) {
                    Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()

                }else{

               // Toast.makeText(this@ChangeRateActivity,base_et.text.toString(),Toast.LENGTH_LONG).show()
                ConvertMoney(base_et.text.toString().toDouble(),base_name.text.toString(),select_name.text.toString())}
            }
            false
        }

    }



    companion object {
        private const val RATE_ITEM_ARG = "rateItemArg"
        private const val BASE_CURR = "basecurrency"

    fun getStartIntentForConverter(context: Context,item:Rate) {
        Intent(context, ChangeRateActivity::class.java).apply {

           putExtra(RATE_ITEM_ARG, item)

            context.startActivity(this)
        }
    }}
    private fun initViews(){
        base_name.text=getString(R.string.base_currency_logo)
        convert_btn.setOnClickListener{
            val input = base_et?.text.toString().trim()
            if (input.isNullOrBlank()) {
                Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_LONG).show()

            }else{
            ConvertMoney(base_et.text.toString().toDouble(),base_name.text.toString(),select_name.text.toString())
            }

        }

    }
    fun ConvertMoney(value:Double,fromCurruency:String,toCurrency:String) {

        CurrencyConverter.calculate(value, fromCurruency, toCurrency, CurrencyConverter.Callback {
                value, e ->
            if (e != null) {

                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            } else {
                Log.e("value",value.toString())
                showResult("%.3f".format(value).toDouble(),fromCurruency,toCurrency)
                Toast.makeText(getApplicationContext(), value.toString(), Toast.LENGTH_LONG).show()

            }
        })


    }

   private fun showResult(value:Double, fromCurruency:String, toCurrency:String){
   val dialog = AlertDialog.Builder(this,R.style.CustomDialogTheme)
        .setTitle( getString(R.string.success_result))
        .setMessage("${base_et.text.toString()}  $fromCurruency".plus(" ")
            .plus(getString(R.string.is_equal)).plus(" ").plus( "$value $toCurrency" ))
        .setPositiveButton(android.R.string.yes) { dialog, which ->

    }.show()

   val message=dialog.findViewById<TextView>(android.R.id.message)
    message?.textSize = 20f



}

    override fun onResume() {
        super.onResume()
        base_et.hideKeyboard()
    }

//Extension fun for hidesoft key board

    fun View.hideKeyboard() {
        val inputMethodManager = applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
