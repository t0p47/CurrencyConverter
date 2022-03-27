package com.t0p47.currencyconverter.di.module

import com.t0p47.currencyconverter.di.WorkManagerKey
import com.t0p47.currencyconverter.factory.worker.ChildWorkerFactory
import com.t0p47.currencyconverter.worker.CurrencyRateUpdateWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkManagerModule {

    @Binds
    @IntoMap
    @WorkManagerKey(CurrencyRateUpdateWorker::class)
    fun currencyRateUpdateWorker(factory: CurrencyRateUpdateWorker.Factory): ChildWorkerFactory

}