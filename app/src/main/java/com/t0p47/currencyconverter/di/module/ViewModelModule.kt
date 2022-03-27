package com.t0p47.currencyconverter.di.module

import androidx.lifecycle.ViewModel
import com.t0p47.currencyconverter.di.ViewModelKey
import com.t0p47.currencyconverter.view.NavViewModel
import com.t0p47.currencyconverter.view.chart.ChartViewModel
import com.t0p47.currencyconverter.view.main.MainViewModel
import com.t0p47.currencyconverter.view.select_currency.SelectCurrencyViewModel
import com.t0p47.currencyconverter.view.splash.SplashViewModel
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun splashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NavViewModel::class)
    abstract fun navViewModel(navViewModel: NavViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectCurrencyViewModel::class)
    abstract fun selectCurrencyViewModel(selectCurrencyViewModel: SelectCurrencyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChartViewModel::class)
    abstract fun chartViewModel(chartViewModel: ChartViewModel): ViewModel

}