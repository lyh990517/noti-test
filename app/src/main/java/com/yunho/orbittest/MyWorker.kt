import android.content.Context
import android.content.Intent
import android.os.PowerManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yunho.orbittest.MyForegroundService
import com.yunho.orbittest.NotificationService

class MyWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private lateinit var wakeLock: PowerManager.WakeLock

    private val notificationService = NotificationService(context)

    override fun doWork(): Result {
        acquireWakeLock()

        val intent = Intent(context, MyForegroundService::class.java)
        context.startForegroundService(intent)

        notificationService.showCompletionNotification()

        releaseWakeLock()

        return Result.success()
    }

    private fun acquireWakeLock() {
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
}
