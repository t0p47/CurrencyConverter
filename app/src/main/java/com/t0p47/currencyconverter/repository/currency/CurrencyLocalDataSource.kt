package com.t0p47.currencyconverter.repository.currency

import android.util.Log
import com.t0p47.currencyconverter.model.enums.CurrencyType
import com.t0p47.currencyconverter.room.dao.CurrencyDao
import com.t0p47.currencyconverter.room.dao.CurrencyModelDao
import com.t0p47.currencyconverter.room.entity.CurrencyEntity
import com.t0p47.currencyconverter.room.entity.CurrencyModelEntity
import com.t0p47.currencyconverter.utils.TAG

class CurrencyLocalDataSource(private val currencyDao: CurrencyDao, private val currencyModelDao: CurrencyModelDao) {

    suspend fun insert(currencyEntity: CurrencyEntity){
        currencyDao.insert(currencyEntity)
    }

    suspend fun getCurrencyEntitiesByCurrencyTypes(currencyTypes: List<CurrencyType>): List<CurrencyEntity>{
        return currencyDao.getCurrencyEntitiesByCurrencyTypes(currencyTypes)
    }

    suspend fun getCurrencyEntityByCurrencyType(inputCurrencyType: CurrencyType): CurrencyEntity{
        return currencyDao.getCurrencyEntityByCurrencyType(inputCurrencyType)
    }

    suspend fun getCurrencyModelEntityByCurrencyType(inputCurrencyType: CurrencyType): CurrencyModelEntity{
        return currencyModelDao.getCurrencyModelEntityByCurrencyType(inputCurrencyType)
    }

    suspend fun makeCurrencyFavorite(currencyModelEntity: CurrencyModelEntity): Int{
        return currencyModelDao.makeCurrencyFavorite(currencyModelEntity)
    }

    //getCurrenciesRateInfo
    suspend fun getCurrenciesRateInfo(): List<CurrencyEntity>{
        return currencyDao.getCurrenciesRateInfo()
    }

    suspend fun getAllCurrenciesInfo(): List<CurrencyModelEntity>{
        return currencyModelDao.getAllCurrenciesInfo()
    }

}