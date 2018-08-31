package id.lagu.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.Activity
import android.support.v4.content.WakefulBroadcastReceiver.startWakefulService
import android.content.ComponentName

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val comp = ComponentName(context?.packageName, AlarmService::class.java.name)
        intent?.putExtra("name", intent.extras.getString("name"))
        intent?.putExtra("id", intent.extras.getString("id"))
        intent?.putExtra("desc", intent.extras.getString("desc"))
        intent?.putExtra("date", intent.extras.getString("date"))
        intent?.putExtra("time", intent.extras.getString("time"))

        startWakefulService(context, intent?.setComponent(comp))
        resultCode = Activity.RESULT_OK
    }

}