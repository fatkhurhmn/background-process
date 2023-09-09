package com.muffar.backgroudprocess


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.muffar.backgroudprocess.worker.NotificationWorker
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private lateinit var workManager: WorkManager

    private val _workStatus = MutableLiveData<String>()
    val workStatus: LiveData<String>
        get() = _workStatus

    fun setWorkManager(workManager: WorkManager) {
        this.workManager = workManager
    }

    fun runNotification() {
        val workRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES).build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )

        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observeForever { workInfo ->
                workInfo?.let {
                    val status = workInfo.state.name
                    _workStatus.postValue(status)
                }
            }
    }

    fun cancelNotification() {
        workManager.cancelUniqueWork(WORK_NAME)
    }

    companion object {
        private const val WORK_NAME = "notification"
    }
}