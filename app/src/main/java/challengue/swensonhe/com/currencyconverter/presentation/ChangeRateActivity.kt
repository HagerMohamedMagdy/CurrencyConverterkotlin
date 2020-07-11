package challengue.swensonhe.com.currencyconverter.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import challengue.swensonhe.com.currencyconverter.R
import challengue.swensonhe.com.currencyconverter.model.Rate
import kotlinx.android.synthetic.main.activity_change_rate.*

class ChangeRateActivity : AppCompatActivity() {
lateinit var rateItem:Rate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_rate)
        base_et.requestFocus()


        if (intent.hasExtra(RATE_ITEM_ARG)) {
            rateItem = intent.getParcelableExtra(RATE_ITEM_ARG)
            select_name.text=rateItem.name
            selected_et.text=rateItem.value.toString()

    }}


    companion object {
            private const val RATE_ITEM_ARG = "rateItemArg"
        private const val BASE_CURR = "basecurrency"

    fun getStartIntentForConverter(context: Context,item:Rate) {
        Intent(context, ChangeRateActivity::class.java).apply {

           putExtra(RATE_ITEM_ARG, item)

            context.startActivity(this)
        }
    }}
}
