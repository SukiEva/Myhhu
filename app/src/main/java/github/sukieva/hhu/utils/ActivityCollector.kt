package github.sukieva.hhu.utils

import android.app.Activity

object ActivityCollector {

    private val activities = ArrayList<Activity>()


    fun addActivity(activity: Activity) = activities.add(activity)

    fun removeActivity(activity: Activity) = activities.remove(activity)

    fun topActivity(): Activity = activities[activities.lastIndex]

    fun finishTopActivity() {
        val topActivity = topActivity()
        if (!topActivity.isFinishing) topActivity.finish()
        removeActivity(topActivity)
    }

    fun finishAllActivity() {
        for (activity in activities) {
            if (!activity.isFinishing)
                activity.finish()
        }
        activities.clear()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

}