package com.t0p47.currencyconverter.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.t0p47.currencyconverter.factory.worker.ChildWorkerFactory
import com.t0p47.currencyconverter.utils.TAG
import javax.inject.Inject

class CurrencyRateUpdateWorker @Inject constructor(private val context: Context, workerParameters: WorkerParameters)
    :CoroutineWorker(context, workerParameters){

    override suspend fun doWork(): Result {
        Log.d(TAG, "CurrencyRateUpdateWorker: doWork")

        return Result.success()
    }

    class Factory @Inject constructor(

    ): ChildWorkerFactory{
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return CurrencyRateUpdateWorker(
                appContext,
                params
            )
        }

    }
}