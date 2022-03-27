package com.t0p47.currencyconverter.model.moshi_adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.t0p47.currencyconverter.model.enums.CurrencyType

class CurrencyTypeAdapter {

    @ToJson
    fun toJson(currencyType: CurrencyType): String{
        return currencyType.name
    }

    @FromJson
    fun fromJson(currencyTypeStr: String): CurrencyType{
        return CurrencyType.valueOf(currencyTypeStr)
    }

}