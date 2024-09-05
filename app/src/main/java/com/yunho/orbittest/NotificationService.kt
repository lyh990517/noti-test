package com.yunho.orbittest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat

class NotificationService(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun showCompletionNotification(): Pair<Int, Notification> {
        playMusic()

        val channelId = "work_manager_channel"
        val notificationId = 1

        val channelName = "WorkManager Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(context, NotiTestActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("test")
            .setContentText("test")
            .setLocalOnly(true)
            .setFullScreenIntent(pendingIntent, true)
            .setOngoing(true)
            .setSound(null)
            .setVibrate(longArrayOf(1000))
            .setContentIntent(pendingIntent) // Set the PendingIntent
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        return notificationId to notification
    }

    private fun playMusic() {
        // Replace `R.raw.your_music_file` with the actual music file in your `res/raw` directory
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
        mediaPlayer?.start()

        // Optional: Set a listener to release the MediaPlayer once the music finishes
        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }
    }
}
