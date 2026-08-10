package com.cranoxz.streamroom.lobby.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cranoxz.streamroom.util.GoogleBlue
import com.cranoxz.streamroom.util.SBlack

@Preview
@Composable
fun LobbyContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.SBlack)
            .padding(16.dp)
    ) {
        Middle(modifier = Modifier.align(Alignment.Center))

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            MBotton(modifier = Modifier.fillMaxWidth(), text = "Create a Party")
            Spacer(Modifier.height(8.dp))
            MBotton(
                modifier = Modifier.fillMaxWidth(),
                text = "Join a Party",
                strokeColor = Color.White,
                background = Color.Transparent
            )
        }
    }
}

@Composable
private fun Middle(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier
                .size(64.dp)
                .background(color = Color.GoogleBlue, shape = RoundedCornerShape(24.dp))
        )
        Text("CoWatch", fontSize = 24.sp, fontWeight = FontWeight.W700, color = Color.White)
    }
}


@Composable
private fun MBotton(
    modifier: Modifier = Modifier,
    text: String = "Join a Party",
    background: Color = Color.GoogleBlue,
    strokeColor: Color = Color.Transparent
) {
    Button(
        modifier = modifier, colors = ButtonDefaults.buttonColors(
            containerColor = background,
        ), onClick = {},
        border = BorderStroke(width = 1.dp, color = strokeColor)
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.W500, fontSize = 16.sp)
    }
}



