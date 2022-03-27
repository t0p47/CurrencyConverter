package com.t0p47.currencyconverter.model

import com.t0p47.currencyconverter.room.entity.CurrencyEntity

data class CurrencyLine(

    val currencyEntity: CurrencyEntity,
    val value: Float

)