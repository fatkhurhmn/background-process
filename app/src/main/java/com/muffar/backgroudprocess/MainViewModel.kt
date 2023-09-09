package com.muffar.backgroudprocess


import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.muffar.backgroudprocess.worker.NotificationWorker
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private lateinit var workManager: WorkManager

    fun setWorkManager(workManager: WorkManager) {
        this.workManager = workManager
    }

    fun runNotification() {
        val workRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES).build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun cancelNotification() {
        workManager.cancelUniqueWork(WORK_NAME)
    }

    companion object {
        private const val WORK_NAME = "notification"
    }
}