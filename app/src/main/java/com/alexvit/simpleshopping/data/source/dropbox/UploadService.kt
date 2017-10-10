package com.alexvit.simpleshopping.data.source.dropbox

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.util.Log
import com.alexvit.simpleshopping.App
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by alexander.vitjukov on 10.10.2017.
 */

class UploadService : JobService() {

    companion object {
        const val MIN: Long = 10 * 1000
        const val MAX: Long = 60 * 1000

        fun schedule(context: Context) {
            val component = ComponentName(context, UploadService::class.java)

            val builder = JobInfo.Builder(0, component)
            builder.setMinimumLatency(MIN)
            builder.setOverrideDeadline(MAX)
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)

            val scheduler: JobScheduler? =
                    context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler
            scheduler?.schedule(builder.build())
        }
    }

    private val TAG = UploadService::class.java.simpleName

    private val compositeSub = CompositeDisposable()

    override fun onStartJob(p0: JobParameters?): Boolean {
        val repo = App.get(applicationContext).appComponent.listsRepository()
        val sub = repo.uploadDb().subscribe()
        compositeSub.add(sub)
        Log.d(TAG, "Job started")
        return true

    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        compositeSub.clear()
        Log.d(TAG, "Job stopped")
        return false
    }
}