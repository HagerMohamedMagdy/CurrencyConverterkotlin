package challengue.swensonhe.com.currencyconverter.presentation

import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import challengue.swensonhe.com.currencyconverter.R
import challengue.swensonhe.com.currencyconverter.model.Rate

import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Created by Hager Magdy on 2021-05-08.
 */
 class CurrencyRateAdapter(private val itemClick: (itemPosition: Int,rateItem:Rate) -> Unit):
    RecyclerView.Adapter<CurrencyRateAdapter.ViewHolder>() {

    var items = ArrayList<Rate>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

     init {
        itemView.apply {  setOnClickListener {
             itemClick.invoke(adapterPosition, items[adapterPosition])
         }

     }

}

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {

      return  items.size}

    override fun onBindViewHolder(holder: CurrencyRateAdapter.ViewHolder, position: Int) {
        val rate = items[position]

        holder.itemView.country_name.text = rate.name ?: ""
        holder.itemView.Country_rate.text= rate.value.toString()

    }


}