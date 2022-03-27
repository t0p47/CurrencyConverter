package com.t0p47.currencyconverter.room.entity

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.t0p47.currencyconverter.adapter.DynamicSearchAdapter
import com.t0p47.currencyconverter.model.enums.CurrencyType
import java.util.*

@Entity
data class CurrencyModelEntity(
    @PrimaryKey(autoGenerate = true)
    var _id: Long? = null,
    var currencyType: CurrencyType,
    var logo: Int,
    var isFavorite: Boolean
): DynamicSearchAdapter.Searchable {
    override fun getSearchCriteria(context: Context): String {
        return context.getString(currencyType.currencyNameRes).toLowerCase(Locale.getDefault())
    }

    fun getName(currencyType: CurrencyType){}
}