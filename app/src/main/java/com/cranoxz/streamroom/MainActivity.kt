package com.cranoxz.streamroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cranoxz.loxer.wrapper.timber
import com.cranoxz.streamroom.core.navigations.AppNavigations
import com.cranoxz.streamroom.ui.theme.CoWatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoWatchTheme {
                AppNavigations()
            }
        }
        timber.i("some log this is")
    }
}

