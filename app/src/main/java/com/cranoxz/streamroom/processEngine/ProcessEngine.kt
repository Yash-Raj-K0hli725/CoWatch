package com.cranoxz.streamroom.processEngine

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.cranoxz.streamroom.R
import com.cranoxz.streamroom.core.domain.IDLE
import com.cranoxz.streamroom.core.domain.Response
import com.cranoxz.streamroom.core.domain.UI
import com.cranoxz.streamroom.core.domain.model.UploadProgress
import com.cranoxz.streamroom.data.remote.model.response.CreateRoom
import com.cranoxz.streamroom.util.Primary
import com.cranoxz.streamroom.util.Secondary

@Preview
@Composable
fun ProcessContent(
    state: UI<out CreateRoom> = IDLE,
    progress: UploadProgress = UploadProgress()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Secondary)
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = null,
                tint = Color.White.copy(0.4f),
                modifier = Modifier.size(24.dp)
            )
            if (state is Response<CreateRoom>)
                Text(
                    state.data.room_name,
                    modifier = Modifier.weight(1f),
                    color = Color.White.copy(0.4f),
                    textAlign = TextAlign.End,
                )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            "Getting it ready",
            fontWeight = FontWeight.W600,
            color = Color.White,
            fontSize = 24.sp
        )

        Text(
            "${(progress.sent * 100f / progress.total).toInt()}%",
            fontSize = 48.sp,
            fontFamily = FontFamily(Font(R.font.inter_extra_bold)),
            fontWeight = FontWeight.W800,
            color = Color.Primary
        )

        LinearProgressIndicator(
            progress = { (progress.sent * 1.0f / progress.total) },
            color = Color.Primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
        )
    }
}