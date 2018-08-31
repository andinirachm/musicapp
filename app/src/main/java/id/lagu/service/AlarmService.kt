package id.lagu.service

import android.app.IntentService
import id.lagu.view.activity.MainActivity
import android.content.Intent
import android.app.PendingIntent
import android.app.NotificationManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import id.lagu.R


class AlarmService : IntentService("Alarm") {
    private var alarmNotificationManager: NotificationManager? = null

    override fun onHandleIntent(intent: Intent) {
        sendNotification(intent.extras.getString("name"),
                intent.extras.getString("id"),
                intent.extras.getString("desc"),
                intent.extras.getString("time"),
                intent.extras.getString("date"))
    }

    private fun sendNotification(msg: String, id: String, desc: String, time: String, date: String) {
        alarmNotificationManager = this
                .getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name", msg)
        intent.putExtra("id", id)
        intent.putExtra("time", time)
        intent.putExtra("date", date)
        intent.putExtra("desc", desc)
        val contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alamNotificationBuilder = NotificationCompat.Builder(
                this).setContentTitle("Music").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
                .setAutoCancel(true)
                .setContentText(msg)


        alamNotificationBuilder.setContentIntent(contentIntent)
        val notif = alamNotificationBuilder.getNotification()
        alarmNotificationManager?.notify(17, notif);
        Log.e("AlarmService", "Notification sent.")


    }
}