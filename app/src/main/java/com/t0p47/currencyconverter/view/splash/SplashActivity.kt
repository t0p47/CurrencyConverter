package com.t0p47.currencyconverter.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.t0p47.currencyconverter.R
import com.t0p47.currencyconverter.databinding.ActivitySplashBinding
import com.t0p47.currencyconverter.extension.DiActivity
import com.t0p47.currencyconverter.extension.injectViewModel
import com.t0p47.currencyconverter.factory.ViewModelFactory
import com.t0p47.currencyconverter.utils.PreferenceHelper
import com.t0p47.currencyconverter.view.main.MainActivity
import com.t0p47.currencyconverter.view.main.MainViewModel
import javax.inject.Inject

class SplashActivity @Inject constructor() : DiActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val SPLASH_SHOW_TIME = 3500L

    @Inject
    lateinit var sharPref: PreferenceHelper

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        viewModel = injectViewModel(viewModelFactory)

        Handler(Looper.getMainLooper()).postDelayed({
            //viewModel.checkFirstTimeLaunch
            if(sharPref.isFirstTimeLaunch()){
                Log.d("LOG_TAG", "SplashActivity: onCreate firstTimeLaunch")
                viewModel.initCurrenciesFirstTime()
                sharPref.setFirstTimeLaunch(false)
            }else{
                Log.d("LOG_TAG", "SplashActivity: onCreate NOT first time")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, SPLASH_SHOW_TIME)

        viewModel.currenciesLoaded.observe(this, { result ->
            if(result){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                //TODO: Need create dialog like SE App
                Toast.makeText(this, getString(R.string.no_internet_first_launch), Toast.LENGTH_LONG).show()
                Log.d("LOG_TAG","SplashActivity: onCreate: NO INTERNET in first launch")
            }
        })
    }
}