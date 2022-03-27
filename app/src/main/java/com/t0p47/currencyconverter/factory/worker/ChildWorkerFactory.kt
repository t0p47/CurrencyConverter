package com.t0p47.currencyconverter.factory.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {

    fun create(appContext: Context, params: WorkerParameters): ListenableWorker

}