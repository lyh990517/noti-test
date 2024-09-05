package com.yunho.orbittest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator

class AlarmReceiver : BroadcastReceiver() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var wakeLock: PowerManager.WakeLock

    override fun onReceive(context: Context, intent: Intent) {
        acquireWakeLock(context)
        startVibration(context)

        context.startForegroundService(Intent(context, MyForegroundService::class.java))
        releaseWakeLock()
    }

    private fun acquireWakeLock(context: Context) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "MyApp::MyWakelockTag"
        )
        wakeLock.acquire(6 * 1000L)
    }

    private fun releaseWakeLock() {
        if (::wakeLock.isInitialized && wakeLock.isHeld) {
            wakeLock.release()
        }
    }

    private fun startVibration(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibrationPattern = longArrayOf(0, 1000, 1000, 1000)
        val vibrationEffect = VibrationEffect.createWaveform(vibrationPattern, 0)
        vibrator.vibrate(vibrationEffect)
    }
}
