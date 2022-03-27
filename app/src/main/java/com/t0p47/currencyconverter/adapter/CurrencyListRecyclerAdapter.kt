package com.t0p47.currencyconverter.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.t0p47.currencyconverter.R
import com.t0p47.currencyconverter.databinding.CurrencyLineHeaderRowBinding
import com.t0p47.currencyconverter.databinding.CurrencyLineSelectRowBinding
import com.t0p47.currencyconverter.model.enums.ListItemType
import com.t0p47.currencyconverter.room.entity.CurrencyModelEntity
import com.t0p47.currencyconverter.utils.TAG
import com.t0p47.currencyconverter.view.main.MainViewModel
import java.lang.IllegalArgumentException
import java.util.*

class CurrencyListRecyclerAdapter(private var currencies: MutableList<CurrencyModelEntity>, private val viewModel: MainViewModel, val context: Context): DynamicSearchAdapter<CurrencyModelEntity>(
    currencies as MutableList<CurrencyModelEntity>,
    context
) {

    private lateinit var listItems: MutableList<ListItem>
    private val originalList = ArrayList(currencies)

    fun filterList(query: String?){
        val searchableList = mutableListOf<CurrencyModelEntity>()
        if(query.isNullOrBlank()){
            searchableList.addAll(originalList)
        }else{
            val searchResults = originalList.filter {
                it.getSearchCriteria(context).contains(query)
            }
            searchableList.addAll(searchResults)
        }
        prepareList(searchableList)
        notifyDataSetChanged()
    }

    private fun prepareList(startList: MutableList<CurrencyModelEntity>){

        //Log.d(TAG, "CurrencyListRecyclerAdapter: prepareList: size: ${startList.size}")

        //val counter = 0

        /*for (currency in currencies){
            Log.d(TAG, "CurrencyListRecyclerAdapter: before: ${currency}, ${currency.isFavorite}")
        }*/

        startList.sortByDescending {
            if(it.isFavorite){
                Log.d("LOG_TAG","CurrencyListRecyclerAdapter: ${it.currencyType} is FAVORITE")
                //counter++
            }
            it.isFavorite
        }


        listItems = mutableListOf()

        var iterInt = 0
        if(startList.isNotEmpty()){
            if(startList[iterInt].isFavorite){
                listItems.add(ListItem(ListItemType.TYPE_HEADER, context.getString(R.string.favorite)))
            }
            while(startList[iterInt].isFavorite){
                listItems.add(ListItem(ListItemType.TYPE_ITEM, startList[iterInt]))
                iterInt += 1
            }
        }


        listItems.add(ListItem(ListItemType.TYPE_HEADER, context.getString(R.string.currencies)))

        for(i in iterInt until startList.size){
            listItems.add(ListItem(ListItemType.TYPE_ITEM, startList[i]))
        }

        //Log.d(TAG,"CurrencyListRecyclerAdapter: favoriteCount: $counter")

    }


    init {

        prepareList(currencies)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when(viewType){
            ListItemType.TYPE_HEADER.ordinal -> {
                val binding = CurrencyLineHeaderRowBinding.inflate(inflater, parent, false)
                return CurrencyLineHeaderViewHolder(binding.root)
            }
            ListItemType.TYPE_ITEM.ordinal -> {
                val binding = CurrencyLineSelectRowBinding.inflate(inflater, parent, false)
                return CurrencyLineViewHolder(binding.root)
            }
            else -> throw IllegalArgumentException("unknown viewType: $viewType")
        }



    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Log.d(TAG,"CurrencyLineRecyclerAdapter: onBindViewHolder: $position, ${listItems[position]}")

        val listItem = listItems[position]

        when(getItemViewType(position)){
            ListItemType.TYPE_HEADER.ordinal -> {
                holder as CurrencyLineHeaderViewHolder

                holder.binding?.header = listItem.item.toString()
            }
            ListItemType.TYPE_ITEM.ordinal -> {
                holder as CurrencyLineViewHolder
                val currencyModelEntity = listItem.item as CurrencyModelEntity

                /*val intDrawableId = if(currencyModelEntity.currencyType.name == "TRY"){
                    context.resources.getIdentifier("edge_try", "mipmap", context.packageName)
                }else{
                    context.resources.getIdentifier(currencyModelEntity.currencyType.name.toLowerCase(), "mipmap", context.packageName)
                }*/


                //Log.d("LOG_TAG","CurrencyListRecyclerAdapter: onBindViewHolder: ${currencyModelEntity.currencyType}")
                holder.binding?.imgLogo?.setImageResource(currencyModelEntity.logo)
                holder.binding?.bigName = currencyModelEntity.currencyType.name
                holder.binding?.smallName = currencyModelEntity.currencyType.currencyNameRes
                holder.binding?.viewModel = viewModel
                holder.binding?.currencyModelEntity = currencyModelEntity

                if(currencyModelEntity.isFavorite){
                    holder.binding?.imgFavorite?.setImageResource(R.drawable.ic_favorite_on)
                }

                holder.binding?.imgFavorite?.setOnClickListener {
                    Log.d("LOG_TAG","CurrencyListRecyclerAdapter: onBindViewHolder: ${currencyModelEntity.currencyType} is favorite: ${currencyModelEntity.isFavorite}")
                    viewModel.toggleCurrencyFavorite(currencyModelEntity)
                    //Log.d("LOG_TAG","CurrencyListRecyclerAdapter: onBindViewHolder: ${currencyModelEntity.currencyType} is favorite(after): ${currencyModelEntity.isFavorite}")

                    /*val currencyInOriginal = currencies.find {
                        it.currencyType == currencyModelEntity.currencyType
                    }*/
                    val currencyIndexInOriginal = currencies.indexOf(currencyModelEntity)
                    currencies[currencyIndexInOriginal].isFavorite = currencyModelEntity.isFavorite
                    prepareList(currencies)
                    notifyDataSetChanged()


                    /*if(currencyModelEntity.isFavorite){
                        (listItems[position].item as CurrencyModelEntity).isFavorite = false
                        Collections.swap(listItems, position, listItems.size-1)
                        //val removedItemIndex = listItems.removeAt(position)


                        /*Log.d("LOG_TAG","CurrencyListRecyclerAdapter: onBindViewHolder: ${currencyModelEntity.currencyType} UNFAVORITE, " +
                                " position: $position, removedItemIndex: $removedItemIndex")*/

                        Log.d("LOG_TAG","CurrencyListRecyclerAdapter: onBindViewHolder: ${currencyModelEntity.currencyType} UNFAVORITE")
                        notifyDataSetChanged()
                    }else{
                        (listItems[position].item as CurrencyModelEntity).isFavorite = true
                        Collections.swap(listItems, position, 1)

                        Log.d("LOG_TAG","CurrencyListRecyclerAdapter: onBindViewHolder: ${currencyModelEntity.currencyType} FAVORITE")
                        notifyDataSetChanged()
                    }*/


                }

            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return listItems[position].listItemType.ordinal
    }

    class CurrencyLineHeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var binding: CurrencyLineHeaderRowBinding? = DataBindingUtil.bind(itemView)
    }

    class CurrencyLineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var binding: CurrencyLineSelectRowBinding? = DataBindingUtil.bind(itemView)
    }

}