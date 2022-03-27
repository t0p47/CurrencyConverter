package com.t0p47.currencyconverter.adapter

import android.content.Context
import android.util.Log
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.t0p47.currencyconverter.model.enums.ListItemType
import com.t0p47.currencyconverter.room.entity.CurrencyModelEntity
import java.util.*
import kotlin.collections.ArrayList

abstract class DynamicSearchAdapter<T: DynamicSearchAdapter.Searchable>(private val searchableList: MutableList<T>, private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    private val originalList = ArrayList(searchableList)

    private var onNothingFound: (() -> Unit)? = null

    fun search(s: String?, onNothingFound: (() -> Unit)?){
        this.onNothingFound = onNothingFound
        filter.filter(s)
    }

    override fun getFilter(): Filter {
        return object: Filter(){

            private val filterResults = FilterResults()

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchableList.clear()
                //Log.d("LOG_TAG","DynamicSearchAdapter: performFiltering: constraint: $constraint")
                if(constraint.isNullOrBlank()){
                    searchableList.addAll(originalList)
                }else{
                    val searchResults = originalList.filter {
                        //Log.d("LOG_TAG","DynamicSearchAdapter: performFiltering: ${it.getSearchCriteria(context)}")
                        it.getSearchCriteria(context).contains(constraint.toString().toLowerCase(Locale.getDefault()))
                    }
                    //Log.d("LOG_TAG","DynamicSearchAdapter: performFiltering: searchResults size: ${searchResults.size}")
                    searchableList.addAll(searchResults)
                }
                return filterResults.also {
                    it.values = searchableList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if(searchableList.isNullOrEmpty()){
                    onNothingFound?.invoke()
                }
                notifyDataSetChanged()
            }

        }
    }

    interface Searchable{
        fun getSearchCriteria(context: Context): String
    }

    data class ListItem(
        var listItemType: ListItemType,
        var item: Any
    )

}