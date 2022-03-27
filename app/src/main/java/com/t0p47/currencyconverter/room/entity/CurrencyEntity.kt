package com.t0p47.currencyconverter.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.t0p47.currencyconverter.model.enums.CurrencyType

@Entity(indices = [Index(value = ["currency"], unique = true)])
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    var _id: Long?,
    var currency: CurrencyType,
    var baseCurrency: CurrencyType,
    var rate: Float
)