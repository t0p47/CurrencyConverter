package com.t0p47.currencyconverter.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.t0p47.currencyconverter.model.enums.CurrencyType
import com.t0p47.currencyconverter.room.entity.CurrencyModelEntity

@Dao
interface CurrencyModelDao {

    @Insert
    suspend fun addNewCurrencies(currencies: List<CurrencyModelEntity>)

    @Query("SELECT * FROM CurrencyModelEntity")
    suspend fun getAllCurrenciesInfo(): List<CurrencyModelEntity>

    @Query("SELECT * FROM CurrencyModelEntity WHERE currencyType = :inputCurrencyType")
    suspend fun getCurrencyModelEntityByCurrencyType(inputCurrencyType: CurrencyType): CurrencyModelEntity

    //TODO: Maybe return Int is not needed
    @Update
    suspend fun makeCurrencyFavorite(currencyModelEntity: CurrencyModelEntity): Int
}