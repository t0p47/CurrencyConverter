package com.t0p47.currencyconverter.view

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.t0p47.currencyconverter.model.NavData
import com.t0p47.currencyconverter.utils.SingleLiveEvent
import javax.inject.Inject

class NavViewModel @Inject constructor(): ViewModel() {

    private var newDestination = SingleLiveEvent<Int>()
    private var newDestinationWithData = SingleLiveEvent<NavData>()

    fun getNewDestination(): LiveData<Int> = newDestination

    fun getNewDestinationWithData() = newDestinationWithData

    fun setNewDestination(destinationId: Int){
        newDestination.value = destinationId
    }

    fun setNewDestinationWithData(destinationId: Int, bundle: Bundle){
        newDestinationWithData.value = NavData(destinationId, bundle)
    }

}