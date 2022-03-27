package com.t0p47.currencyconverter.ads

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.t0p47.currencyconverter.view.main.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

//TODO: Make more new DI system
@Singleton
class AdsController @Inject constructor(): LifecycleObserver {

    companion object{
        internal val DOWN_BANNER_PLACE_INTERVAL = 30000L //30 seconds
    }

    private var upDownAds: UpDownBanner? = null
    private lateinit var activity: MainActivity

    fun enableAds(activity: MainActivity){
        this.activity = activity
        upDownAds = UpDownBanner(activity)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onUnbind(owner: LifecycleOwner){
        disableAds()
    }

    fun disableAds(){
        upDownAds?.disableUpDownAds()

        upDownAds = null
    }

}