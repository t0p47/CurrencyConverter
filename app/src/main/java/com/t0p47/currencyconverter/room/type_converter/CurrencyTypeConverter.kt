package com.t0p47.currencyconverter.room.type_converter

import androidx.room.TypeConverter
import com.t0p47.currencyconverter.model.enums.CurrencyType

class CurrencyTypeConverter {

    @TypeConverter
    fun currencyTypeToInt(currencyType: CurrencyType): Int{
        return currencyType.ordinal
    }

    @TypeConverter
    fun intToCurrencyType(ord: Int): CurrencyType {
        return CurrencyType.values()[ord]
    }

}