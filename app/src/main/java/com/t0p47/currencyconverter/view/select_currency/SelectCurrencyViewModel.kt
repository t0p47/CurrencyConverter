package com.t0p47.currencyconverter.view.select_currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t0p47.currencyconverter.repository.currency.CurrencyRepository
import com.t0p47.currencyconverter.room.entity.CurrencyModelEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCurrencyViewModel @Inject constructor(private val currencyRepository: CurrencyRepository) : ViewModel() {

    /*
    * private val _watchedCurrencyTypes = MutableLiveData<List<CurrencyLine>>()
    val watchedCurrencyTypes: LiveData<List<CurrencyLine>>
        get() = _watchedCurrencyTypes
    * */
    private val _currencyModelEntities = MutableLiveData<List<CurrencyModelEntity>>()
    val currencyModelEntities: LiveData<List<CurrencyModelEntity>>
        get() = _currencyModelEntities


    fun getAllCurrenciesInfo(){

        viewModelScope.launch {

            _currencyModelEntities.postValue(currencyRepository.localDataSource.getAllCurrenciesInfo())

        }

    }

}
