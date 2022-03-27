package com.t0p47.currencyconverter

import android.app.Application
import com.t0p47.currencyconverter.di.DaggerAppComponent
import com.t0p47.currencyconverter.extension.autoInject
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CurrencyConverterApp: Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        // Инициализация AppMetrica SDK
        val configBuilder =
            YandexMetricaConfig.newConfigBuilder(getString(R.string.yandex_sdk_api_key))
        configBuilder.withLogs()

        YandexMetrica.activate(applicationContext, configBuilder.build())
        // Отслеживание активности пользователей
        YandexMetrica.enableActivityAutoTracking(this)

        DaggerAppComponent
            .builder()
            .context(this)
            .build()
            .inject(this)

        autoInject()

    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}