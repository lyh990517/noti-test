package com.yunho.orbittest

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.yunho.orbittest.ui.theme.OrbitTestTheme

class NotiTestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFlag()
        enableEdgeToEdge()
        setContent {
            OrbitTestTheme {
                Text(text = "success")
            }
        }
    }

    private fun setFlag() {
        val win = window
        win.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or  // 잠금화면 위에 표시
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or  // 잠금화면 해제
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or    // 화면 유지
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
    }
}
