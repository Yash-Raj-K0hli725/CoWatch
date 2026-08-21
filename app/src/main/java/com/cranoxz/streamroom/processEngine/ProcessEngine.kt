package com.cranoxz.streamroom.processEngine

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
    videoUri: Uri = "".toUri(),
    progress: UploadProgress = UploadProgress()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Secondary)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = null,
                tint = Color.White.copy(0.4f),
                modifier = Modifier.size(24.dp)
            )
            if (state is Response<CreateRoom>) Text(
                state.data.room_name,
                modifier = Modifier.weight(1f),
                color = Color.White.copy(0.4f),
                textAlign = TextAlign.End,
            )
        }

        Text(
            stringResource(R.string.getting_it_ready),
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.lato_bold)),
            color = Color.White
        )

        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {

            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 32.sp)){
                        append("${(progress.sent * 100f / progress.total).toInt()}")
                    }
                    withStyle(SpanStyle(fontSize = 12.sp)) {
                        append("%")
                    }
                },
                modifier = Modifier.align(Alignment.Start),
                color = Color.Primary,
                fontWeight = FontWeight.W800
            )
            LinearProgressIndicator(
                progress = { (progress.sent * 1f / progress.total) },
                modifier = Modifier.fillMaxWidth().height(16.dp),
                color = Color.Primary,
                trackColor = Color.Secondary
            )
        }
    }
}