package com.t0p47.currencyconverter.view.main

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.t0p47.currencyconverter.R
import com.t0p47.currencyconverter.ads.AdsController
import com.t0p47.currencyconverter.databinding.ActivityMainBinding
import com.t0p47.currencyconverter.extension.DiActivity
import com.t0p47.currencyconverter.extension.injectViewModel
import com.t0p47.currencyconverter.factory.ViewModelFactory
import com.t0p47.currencyconverter.inapp_purchase.BillingManager
import com.t0p47.currencyconverter.utils.PreferenceHelper
import com.t0p47.currencyconverter.view.NavViewModel
import com.yandex.mobile.ads.banner.BannerAdView
import javax.inject.Inject

class MainActivity : DiActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sharPref: PreferenceHelper

    @Inject
    lateinit var billingManager: BillingManager

    @Inject
    lateinit var adsController: AdsController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var navViewModel: NavViewModel
    private lateinit var viewModel: MainViewModel

    lateinit var downAdView: BannerAdView

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navViewModel = ViewModelProvider(this)[NavViewModel::class.java]
        viewModel = injectViewModel(viewModelFactory)

        downAdView = binding.adView

        lifecycle.addObserver(billingManager)

        lifecycle.addObserver(adsController)

        //supportActionBar?.setDisplayHomeAsUpEnabled(true);
        //supportActionBar?.setDisplayShowHomeEnabled(true);
        setSupportActionBar(binding.toolbar)

        //navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //NavigationUI.setupActionBarWithNavController(this, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController)

        observeLiveData()


        adsController.enableAds(this)
    }

    private fun observeLiveData(){
        billingManager.purchaseUpdateEvent.observe(this) { purchase ->

            Log.d("LOG_TAG", "MainActivity: observeLiveData: purchase: $purchase")
            if (purchase.sku == "update_to_professional") {
                adsController.disableAds()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        observeNavLiveData()
    }

    private fun observeNavLiveData(){
        navViewModel.getNewDestination().observe(this, Observer {
            navController.navigate(it)
        })

        navViewModel.getNewDestinationWithData().observe(this, Observer {
            navController.navigate(it.id, it.bundle)
        })
    }

    override fun onPause() {
        //sharPref.storeInputValue(viewModel.inputValue)
        //sharPref.storeInputCurrencyType(viewModel.inputCurrencyEntity.currency.ordinal)
        sharPref.storeCalculateCurrencyTypes(viewModel.calculateCurrencyEntityList.map { it.currency })
        Log.d(com.t0p47.currencyconverter.utils.TAG,"MainActivity: onPause: ${viewModel.inputValue}")

        super.onPause()
    }
}
