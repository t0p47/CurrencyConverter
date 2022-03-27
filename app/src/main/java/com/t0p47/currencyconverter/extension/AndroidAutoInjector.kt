package com.t0p47.currencyconverter.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.t0p47.currencyconverter.CurrencyConverterApp
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

interface FragmentInjectable

@SuppressLint("Registered")
open class DiActivity: AppCompatActivity(), HasAndroidInjector {

    @Inject
    internal lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

}

fun CurrencyConverterApp.autoInject(){

    this.registerActivityLifecycleCallbacks(object: Application.ActivityLifecycleCallbacks{

        override fun onActivityCreated(activity: Activity, p1: Bundle?) {

            activity.also {
                if(it is HasAndroidInjector) AndroidInjection.inject(it)

                if(it is FragmentActivity){
                    it.supportFragmentManager.registerFragmentLifecycleCallbacks(object:
                        FragmentManager.FragmentLifecycleCallbacks(){
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if(f is FragmentInjectable) AndroidSupportInjection.inject(f)
                        }
                    }, true)
                }
            }

        }

        override fun onActivityPaused(p0: Activity) {}

        override fun onActivityStarted(p0: Activity) {}

        override fun onActivityDestroyed(p0: Activity) {}

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}

        override fun onActivityStopped(p0: Activity) {}

        override fun onActivityResumed(p0: Activity) {}
    })

}