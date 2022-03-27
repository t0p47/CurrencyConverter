package com.t0p47.currencyconverter.ads

import android.util.Log
import com.t0p47.currencyconverter.ads.AdsController.Companion.DOWN_BANNER_PLACE_INTERVAL
import com.t0p47.currencyconverter.view.main.MainActivity
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import java.util.*
import kotlin.concurrent.timer

class UpDownBanner(private val activity: MainActivity) {

    private var downRefreshTimer: Timer? = null

    private val adRequest = AdRequest.Builder().build()

    init {
        enableDownTimer()
    }

    private fun enableDownTimer(){
        //activity.downAdView.setBlockId("R-M-DEMO-320x50")
        activity.downAdView.setBlockId("R-M-930251-1")

        activity.downAdView.setAdSize(AdSize.BANNER_320x50)
        activity.downAdView.setBannerAdEventListener(bannerAdEventListener)

        downRefreshTimer = timer(
            name = "down_refresh",
            daemon = false,
            initialDelay = 10000,
            period = DOWN_BANNER_PLACE_INTERVAL
        ){
            Log.d("ADS_TAG", "UpDownBanner: downRefreshTimer: loadAd")
            activity.downAdView.loadAd(adRequest)
        }
    }

    private val bannerAdEventListener = object: BannerAdEventListener{
        override fun onAdLoaded() {
            Log.d("ADS_TAG", "UpDownBanner: enableDownTimer: onAdLoaded")
        }

        override fun onAdFailedToLoad(error: AdRequestError) {
            Log.d("ADS_TAG", "UpDownBanner: enableDownTimer: onAdFailedToLoad code: ${error.code}, description: ${error.description}")
        }

        override fun onLeftApplication() {
            Log.d("ADS_TAG", "UpDownBanner: enableDownTimer: onAdLeftApplication")
        }

        override fun onReturnedToApplication() {
            Log.d("ADS_TAG", "UpDownBanner: enableDownTimer: onReturnedToApplication")
        }
    }

    fun disableUpDownAds(){
        downRefreshTimer?.cancel()
        downRefreshTimer = null
    }

}