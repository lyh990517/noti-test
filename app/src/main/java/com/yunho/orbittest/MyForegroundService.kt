package com.yunho.orbittest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()

        // 알림 채널 생성 (Android 8.0 이상에서 필요)
        createNotificationChannel()

        // 포그라운드 알림 생성
        val notification: Notification = NotificationCompat.Builder(this, "foreground_service_channel")
            .setContentTitle("포그라운드 서비스")
            .setContentText("백그라운드 작업 중입니다.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 백그라운드 작업 수행
        // 작업이 끝나면 stopSelf() 호출 가능

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
