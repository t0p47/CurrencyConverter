package com.t0p47.currencyconverter.room.dao

import androidx.room.*
import com.t0p47.currencyconverter.model.enums.CurrencyType
import com.t0p47.currencyconverter.room.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyEntity: CurrencyEntity)

    @Query("SELECT * FROM CurrencyEntity")
    suspend fun getCurrenciesRateInfo(): List<CurrencyEntity>

    @Query("SELECT * FROM CurrencyEntity WHERE currency IN (:currencyTypes)")
    suspend fun getCurrencyEntitiesByCurrencyTypes(currencyTypes: List<CurrencyType>): List<CurrencyEntity>

    //getCurrencyEntityByCurrencyType(inputCurrencyType)
    @Query("SELECT * FROM CurrencyEntity WHERE currency =:inputCurrencyType")
    suspend fun getCurrencyEntityByCurrencyType(inputCurrencyType: CurrencyType): CurrencyEntity
}