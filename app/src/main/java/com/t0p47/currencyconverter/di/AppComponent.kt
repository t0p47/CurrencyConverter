package com.t0p47.currencyconverter.di

import com.t0p47.currencyconverter.CurrencyConverterApp
import com.t0p47.currencyconverter.di.module.AppModule
import com.t0p47.currencyconverter.di.module.DatabaseModule
import com.t0p47.currencyconverter.di.module.ViewModelModule
import com.t0p47.currencyconverter.di.module.WorkManagerModule
import com.t0p47.currencyconverter.factory.worker.SampleWorkerFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ViewModelModule::class, DatabaseModule::class, WorkManagerModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(app: CurrencyConverterApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: CurrencyConverterApp)

    fun factory(): SampleWorkerFactory

}