package com.yunho.orbittest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.Calendar

@Composable
fun TestScreen(viewModel: OrbitViewModel) {
    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            SideEffect.Reserve -> {
                Toast.makeText(context, "reserve", Toast.LENGTH_SHORT).show()
                scheduleAlarm(context)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { viewModel.reserve() }
        )
        {
            Text(text = "Reserve")
        }
    }
}

fun scheduleAlarm(context: Context) {
    if (!Settings.canDrawOverlays(context)) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${context.packageName}")
        )
        context.startActivity(intent)
    } else {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.SECOND, 10)  // 1분 후에 알람 발생
        }

        // AlarmManager 설정
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // 알람 설정 (RTC_WAKEUP: 장치가 슬립 상태라도 깨워서 실행)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}
