package com.cranoxz.streamroom

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.cranoxz.streamroom.core.domain.Message
import com.cranoxz.streamroom.core.navigations.AppNavigations
import com.cranoxz.streamroom.room.viewmodel.RoomViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val model by viewModels<RoomViewmodel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                AppNavigations()
            }
        }
    }

    fun showToast(message: Message<*>) {
        Toast.makeText(this, message.text, Toast.LENGTH_SHORT).show()
    }
}

