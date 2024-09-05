package com.yunho.orbittest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat

class MyForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        val notificationService = NotificationService(this)
        val (id, notification) = notificationService.showCompletionNotification()

        startForeground(id, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("onStartCommand", "onStartCommand")

        startActivity(
            Intent(this, NotiTestActivity::class.java)
                .apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                }
        )

        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "foreground_service_channel",
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
    }
}
