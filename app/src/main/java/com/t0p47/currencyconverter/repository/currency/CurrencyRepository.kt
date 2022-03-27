package com.t0p47.currencyconverter.repository.currency

import com.t0p47.currencyconverter.room.dao.CurrencyDao
import com.t0p47.currencyconverter.room.dao.CurrencyModelDao
import javax.inject.Inject

class CurrencyRepository @Inject constructor(currencyDao: CurrencyDao, currencyModelDao: CurrencyModelDao) {

    val localDataSource = CurrencyLocalDataSource(currencyDao, currencyModelDao)
    val remoteDataSource = CurrencyRemoteDataSource()

}