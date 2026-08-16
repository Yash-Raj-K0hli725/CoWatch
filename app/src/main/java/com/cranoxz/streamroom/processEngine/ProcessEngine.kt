package com.cranoxz.streamroom.processEngine

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri

@Preview
@Composable
fun ProcessContent(
    partyName: String = "",
    videoUri: Uri = "".toUri(),
) {
    Box(modifier = Modifier.fillMaxSize()) {

    }
}