package com.t0p47.currencyconverter.utils

import android.content.SharedPreferences
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.t0p47.currencyconverter.CurrencyConverterApp
import com.t0p47.currencyconverter.model.enums.CurrencyType
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject constructor(app: CurrencyConverterApp){

    private var pref: SharedPreferences
    private var editor:SharedPreferences.Editor

    var PRIVATE_MODE = 0

    private val PREF_NAME = "CurrencyConverterApp"

    companion object{
        const val INPUT_VALUE_KEY ="input_value_key"
        const val INPUT_CURRENCY_TYPE_ORDINAL_KEY = "input_currency_type_ordinal_key"
        const val CALCULATE_CURRENCY_TYPE_KEY = "calculate_currency_type_key"
        const val RECENT_CURRENCY_TYPE_KEY = "recent_currency_type_key"
        const val LAST_REFRESH_RATE_TIME = "last_refresh_rate_time"
        const val FIRST_TIME_LAUNCH = "first_time_launch"
    }

    init {
        pref = app.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean){
        editor.putBoolean(FIRST_TIME_LAUNCH, isFirstTime).commit()
    }

    fun isFirstTimeLaunch(): Boolean{
        return pref.getBoolean(FIRST_TIME_LAUNCH, true)
    }

    fun storeRefreshRateTime(){
        Log.d("LOG_TAG","PreferenceHelper: storeRefreshRateTime, update currencies")
        val currentTimestamp = Calendar.getInstance().timeInMillis
        editor.putLong(LAST_REFRESH_RATE_TIME, currentTimestamp).commit()
    }

    fun checkRefreshRateTime(): Boolean{
        val currentTimestamp = Calendar.getInstance().timeInMillis
        val lastRefreshTime = pref.getLong(LAST_REFRESH_RATE_TIME, 0L)

        val lastRefreshTimeHours = TimeUnit.MILLISECONDS.toHours(lastRefreshTime)
        val currentTimeHours = TimeUnit.MILLISECONDS.toHours(currentTimestamp)
        Log.d("LOG_TAG","PreferenceHelper: checkRefreshRateTime: lastRefreshTimeHours: $lastRefreshTimeHours, currentTimeHours: $currentTimeHours")

        return currentTimeHours-lastRefreshTimeHours > 4


    }

    fun storeInputValue(input: Float){
        editor.putFloat(INPUT_VALUE_KEY, input).commit()
    }

    fun restoreInputValue(): Float{
        return pref.getFloat(INPUT_VALUE_KEY, 0f)
    }

    fun storeInputCurrencyType(currencyTypeOrdinal: Int){
        editor.putInt(INPUT_CURRENCY_TYPE_ORDINAL_KEY, currencyTypeOrdinal).commit()
    }

    fun restoreInputCurrencyType(): CurrencyType{
        return CurrencyType.values()[pref.getInt(INPUT_CURRENCY_TYPE_ORDINAL_KEY, 149)]
    }

    fun storeCalculateCurrencyTypes(currencyTypes: List<CurrencyType>){
        var saveStr = ""
        for (currencyType in currencyTypes){
            saveStr += "${currencyType.ordinal},"
        }
        editor.putString(CALCULATE_CURRENCY_TYPE_KEY, saveStr.trimEnd(',')).commit()
    }

    fun restoreCalculateCurrencyTypes(): List<CurrencyType>{
        val strRes = pref.getString(CALCULATE_CURRENCY_TYPE_KEY, "49,46")

        val list: List<String>? = strRes?.split(",")

        Log.d(TAG,"PreferenceHelper: restoreCalculateCurrencyTypes: $list, firstVal: ${list?.get(0)}")

        val currencyList = mutableListOf<CurrencyType>()

        if (list != null) {
            if(list.isNotEmpty()){
                for (ordinal in list){
                    currencyList.add(CurrencyType.values()[ordinal.toInt()])
                }
            }else{
                currencyList.add(CurrencyType.SGD)
                currencyList.add(CurrencyType.THB)
            }
        }

        return currencyList
    }

    fun restoreRecentCurrencyTypes(): List<CurrencyType>{
        val strRes = pref.getString(RECENT_CURRENCY_TYPE_KEY,"-1")
        if(strRes == "-1"){
            return emptyList()
        }
        val list: List<String>? = strRes?.split(",")



        list?.forEach { it
            Log.d("LOG_TAG","PreferenceHelper: restoreRecentCurrencyTypes: list: ${it}")
        }

        val currencyList = mutableListOf<CurrencyType>()

        if(list != null){
            if(list.isNotEmpty()){
                for(ordinal in list){
                    currencyList.add(CurrencyType.values()[ordinal.toInt()])
                }
            }
        }

        return currencyList
    }

    fun storeRecentCurrencyTypes(currencyTypes: List<CurrencyType>){
        var saveStr = ""
        for(currencyType in currencyTypes){
            saveStr += "${currencyType.ordinal},"
        }
        editor.putString(RECENT_CURRENCY_TYPE_KEY, saveStr.trimEnd(',')).commit()
    }

    /*fun setList(key: String, list: List<Any>?){

        val moshi = Moshi.Builder().build()

        val type = Types.newParameterizedType(List::class.java, CurrencyType::class.java)
        val jsonAdapter: JsonAdapter<List<Any>> = moshi.adapter(type)

        val json = jsonAdapter.toJson(list)

        editor.putString(key, json)
    }

    fun restoreList(key: String): List<Any>? {
        var list: List<Any>? = null

        val serializedObject = pref.getString(key, null)
        if(serializedObject != null){
            Log.d("LOG_TAG","PreferenceHelper: restoreList: serializedObj: $serializedObject")
            val moshi = Moshi.Builder().build()

            val type = Types.newParameterizedType(List::class.java, CurrencyType::class.java)
            val jsonAdapter: JsonAdapter<List<Any>> = moshi.adapter(type)

            list = jsonAdapter.fromJson(serializedObject)
        }
        return list
    }*/
}