package com.t0p47.currencyconverter.di

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkManagerKey(val value: KClass<out ListenableWorker>)