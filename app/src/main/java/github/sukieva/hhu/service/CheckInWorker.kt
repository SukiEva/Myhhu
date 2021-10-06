package github.sukieva.hhu.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CheckInWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        TODO("Not yet implemented")
    }


}