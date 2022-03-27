package com.t0p47.currencyconverter.di.module

import com.t0p47.currencyconverter.di.module.activity_module.MainActivityModule
import com.t0p47.currencyconverter.di.module.activity_module.SettingsActivityModule
import com.t0p47.currencyconverter.di.module.activity_module.SplashActivityModule
import com.t0p47.currencyconverter.di.scope.ActivityScope
import com.t0p47.currencyconverter.view.main.MainActivity
import com.t0p47.currencyconverter.view.settings.SettingsActivity
import com.t0p47.currencyconverter.view.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SettingsActivityModule::class])
    abstract fun settingsActivityInjector(): SettingsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivityInjector(): SplashActivity

    @Module
    companion object{



    }

}