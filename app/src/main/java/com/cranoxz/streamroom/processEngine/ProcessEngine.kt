package com.cranoxz.streamroom.processEngine

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.cranoxz.streamroom.core.domain.model.UploadProgress
import com.cranoxz.streamroom.util.Primary
import com.cranoxz.streamroom.util.Secondary

@Preview
@Composable
fun ProcessContent(
    partyName: String = "",
    videoUri: Uri = "".toUri(),
    progress: UploadProgress = UploadProgress()
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Secondary)) {
        CircularProgressIndicator(
            progress = { (progress.sent.toFloat() / progress.total) },
            color = Color.Primary,
            strokeWidth = 24.dp,
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.Center)
        )
    }
}