package com.t0p47.currencyconverter.model

import com.t0p47.currencyconverter.model.enums.CurrencyType

data class CurrencyChangeModel(
    var currencyType: CurrencyType,
    var lineNum: Int
)