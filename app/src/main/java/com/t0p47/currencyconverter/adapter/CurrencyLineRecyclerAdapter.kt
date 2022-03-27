package com.t0p47.currencyconverter.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.t0p47.currencyconverter.databinding.CurrencyRowBinding
import com.t0p47.currencyconverter.model.CurrencyLine
import com.t0p47.currencyconverter.utils.TAG
import com.t0p47.currencyconverter.view.NavViewModel
import com.t0p47.currencyconverter.view.main.MainFragmentDirections
import com.t0p47.currencyconverter.view.main.MainViewModel
import java.text.DecimalFormat
import java.util.*

class CurrencyLineRecyclerAdapter constructor(private val viewModel: MainViewModel, private val navViewModel: NavViewModel, private val rowHeight: Int, private val context: Context): ListAdapter<CurrencyLine, CurrencyLineRecyclerAdapter.CurrencyLineViewHolder>(
    CURRENCY_COMPARATOR) {

    val format = DecimalFormat("0.##")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyLineViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = CurrencyRowBinding.inflate(inflater, parent, false)
        return CurrencyLineViewHolder(binding.root)
    }



    override fun onBindViewHolder(holder: CurrencyLineViewHolder, position: Int) {
        val currencyLine = getItem(position)

        val currencyAndroid = Currency.getInstance(currencyLine.currencyEntity.currency.toString().toLowerCase(Locale.getDefault()))

        //Set height
        /*val layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.height=rowHeight
        layoutParams.setMargins(0,0,0,10)

        Log.d(TAG,"CurrencyLineRecyclerAdapter: onBindViewHolder: rowHeight: $rowHeight, position: $position")*/

        //holder.binding?.root?.layoutParams = layoutParams

        //TODO: Work with currency symbols
        holder.binding?.bigName = currencyLine.currencyEntity.currency.toString()
        //holder.binding?.bigName = currencyAndroid.symbol
        holder.binding?.tvCurrencySymbol?.text = currencyAndroid.symbol


        holder.binding?.smallName = currencyLine.currencyEntity.currency.currencyNameRes

        val intDrawableId = context.resources.getIdentifier(currencyLine.currencyEntity.currency.name.toLowerCase(Locale.getDefault()), "mipmap", context.packageName)
        holder.binding?.imgCurrency?.setImageResource(intDrawableId)

        var currencyResult = format.format(currencyLine.value)
        if(currencyResult.length > 15){
            currencyResult = "${currencyResult.substring(0, 9)}..."
            Log.d("LOG_TAG","CurrencyLineRecyclerAdapter: onBindViewHolder: currencyResult larger than ten: $currencyResult")
        }
        holder.binding?.tvCurrencyRate?.text = currencyResult

        holder.binding?.root?.setOnClickListener{
            Log.d(TAG, "CurrencyLineRecyclerAdapter: onBindViewHolder: position: $position")

            viewModel.changeLine = position+2
            navViewModel.setNewDestination(MainFragmentDirections.actionMainFragmentToSelectCurrencyFragment().actionId)
        }
    }

    /*override fun getItemId(position: Int): Long {
        val currencyLine = currencyLines[position]

        Log.d(TAG, "CurrencyLineRecyclerAdapter: getItemId: ${(currencyLine.currencyEntity._id?:0) + position}")

        //return (currencyLine.currencyEntity._id?:0) + position
        return position.toLong()
    }*/

    class CurrencyLineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var binding: CurrencyRowBinding? = DataBindingUtil.bind(itemView)
    }

    companion object{

        private val CURRENCY_COMPARATOR = object: DiffUtil.ItemCallback<CurrencyLine>(){
            override fun areItemsTheSame(oldItem: CurrencyLine, newItem: CurrencyLine): Boolean {
                return oldItem.currencyEntity.currency.ordinal == newItem.currencyEntity.currency.ordinal
            }

            override fun areContentsTheSame(oldItem: CurrencyLine, newItem: CurrencyLine): Boolean {
                return oldItem.currencyEntity.currency == newItem.currencyEntity.currency && oldItem.value == newItem.value
            }
        }

    }

}