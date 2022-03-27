package com.t0p47.currencyconverter.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.t0p47.currencyconverter.room.dao.CurrencyDao
import com.t0p47.currencyconverter.room.dao.CurrencyModelDao
import com.t0p47.currencyconverter.room.entity.CurrencyEntity
import com.t0p47.currencyconverter.room.entity.CurrencyModelEntity
import com.t0p47.currencyconverter.room.type_converter.CurrencyTypeConverter

@Database(entities = [CurrencyEntity::class, CurrencyModelEntity::class], version = AppDatabase.VERSION, exportSchema = false)
@TypeConverters(CurrencyTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    companion object{
        const val DB_NAME = "currency_converter.db"
        const val VERSION = 1
    }

    abstract fun currencyDao():CurrencyDao
    abstract fun currencyModelDao(): CurrencyModelDao

}