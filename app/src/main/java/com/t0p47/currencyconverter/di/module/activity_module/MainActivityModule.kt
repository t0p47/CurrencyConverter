package com.t0p47.currencyconverter.di.module.activity_module

import com.t0p47.currencyconverter.di.module.fragment_module.ChartFragmentModule
import com.t0p47.currencyconverter.di.module.fragment_module.LinesDialogFragmentModule
import com.t0p47.currencyconverter.di.module.fragment_module.MainFragmentModule
import com.t0p47.currencyconverter.di.module.fragment_module.SelectCurrencyFragmentModule
import com.t0p47.currencyconverter.di.scope.FragmentScope
import com.t0p47.currencyconverter.view.chart.ChartFragment
import com.t0p47.currencyconverter.view.dialog.LinesDialogFragment
import com.t0p47.currencyconverter.view.main.MainFragment
import com.t0p47.currencyconverter.view.select_currency.SelectCurrencyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [ChartFragmentModule::class])
    abstract fun chartFragment(): ChartFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun mainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SelectCurrencyFragmentModule::class])
    abstract fun selectCurrency(): SelectCurrencyFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LinesDialogFragmentModule::class])
    abstract fun linesDialog(): LinesDialogFragment

}