package com.t0p47.currencyconverter.model

import com.squareup.moshi.Json

data class LatestCurrency(
    @Json(name="rates") val rates: Rates,
    @Json(name="base") val baseCurrency: String,
    @Json(name="date") val date: String
)