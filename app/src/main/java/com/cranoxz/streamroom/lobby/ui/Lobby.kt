package com.cranoxz.streamroom.lobby.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cranoxz.streamroom.R
import com.cranoxz.streamroom.core.domain.IDLE
import com.cranoxz.streamroom.core.domain.Loadin
import com.cranoxz.streamroom.core.domain.UI
import com.cranoxz.streamroom.ui.common.Botton
import com.cranoxz.streamroom.ui.common.dashedBorder
import com.cranoxz.streamroom.util.Primary
import com.cranoxz.streamroom.util.SBlack
import com.cranoxz.streamroom.util.Secondary
import com.cranoxz.streamroom.util.getFileName

@Preview
@Composable
fun LobbyContent(
    state: UI = IDLE,
    partyName: String = "",
    onNameChange: (String) -> Unit = {},
    onCreate: () -> Unit = {},
    toJoin: () -> Unit = {},
    fileUri: Uri? = null,
    onFileSelect: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Secondary)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text("C", fontSize = 18.sp, fontWeight = FontWeight.W700, color = Color.White)
            }
            Spacer(Modifier.width(8.dp))
            Text(
                stringResource(R.string.app_name),
                fontWeight = FontWeight.W800,
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Botton(
                background = Color.White,
                padding = PaddingValues(horizontal = 12.dp),
                onClick = toJoin
            ) {
                Text(
                    "Join with code?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Secondary
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        PartyConfig(
            modifier = Modifier.weight(1f),
            partyName = partyName,
            onNameChange = onNameChange,
            fileUri = fileUri,
            onFileSelect = onFileSelect
        )
        Spacer(Modifier.height(8.dp))
        Botton(
            modifier = Modifier.fillMaxWidth(),
            isLoading = state is Loadin,
            onClick = onCreate,
            background = Color.Primary,
            strokeColor = Color.Transparent
        ) {
            Text(
                stringResource(R.string.create_a_party),
                fontSize = 18.sp,
                fontWeight = FontWeight.W800
            )
        }
    }
}

@Composable
private fun PartyConfig(
    modifier: Modifier,
    partyName: String,
    onNameChange: (String) -> Unit,
    fileUri: Uri?,
    onFileSelect: () -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(
            partyName,
            onValueChange = onNameChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Primary,
                focusedContainerColor = Color.SBlack,
                unfocusedContainerColor = Color.SBlack,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
            placeholder = {
                Text(
                    stringResource(R.string.new_party_name),
                    fontSize = 12.sp,
                    color = Color.White.copy(0.4f)
                )
            }, maxLines = 1, singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        Uploader(fileUri, onFileSelect)
    }
}

@Composable
private fun Uploader(uri: Uri?, onClick: () -> Unit) {
    Text(stringResource(R.string.video_file), fontSize = 16.sp, color = Color.White.copy(0.4f))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .dashedBorder(color = Color.Primary)
            .background(color = Color.Primary.copy(0.1f))
            .clickable(onClick = onClick)
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            if (uri == null) {
                Text(
                    stringResource(R.string.choose_a_video),
                    color = Color.White,
                    fontWeight = FontWeight.W600
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "MP4 . up to 500 MB",
                    color = Color.White.copy(0.4f),
                    fontWeight = FontWeight.W600
                )
            } else {
                val context = LocalContext.current
                Text(
                    getFileName(context, uri),
                    color = Color.White,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}





