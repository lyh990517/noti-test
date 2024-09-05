package com.yunho.orbittest

import MyWorker
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.concurrent.TimeUnit

@Composable
fun TestScreen(viewModel: OrbitViewModel) {
    val context = LocalContext.current
    viewModel.collectSideEffect {
        when (it) {
            SideEffect.Reserve -> {
                Toast.makeText(context, "reserve", Toast.LENGTH_SHORT).show()
                scheduleWork(context)
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

fun scheduleWork(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiresCharging(false)
        .setRequiresDeviceIdle(false)
        .setRequiresBatteryNotLow(false)
        .build()

    val workRequest: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
        .setInitialDelay(10, TimeUnit.SECONDS)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}
