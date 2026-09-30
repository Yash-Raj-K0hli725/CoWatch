package com.cranoxz.streamroom.core.navigations

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cranoxz.streamroom.core.domain.IDLE
import com.cranoxz.streamroom.core.domain.progress
import com.cranoxz.streamroom.core.navigations.routes.ProcessEngine
import com.cranoxz.streamroom.lobby.ui.LobbyContent
import com.cranoxz.streamroom.lobby.viewmodel.Viewmodel
import com.cranoxz.streamroom.processEngine.ProcessContent
import com.cranoxz.streamroom.processEngine.ProcessEngineModel
import com.cranoxz.streamroom.toaster.Toaster
import com.cranoxz.streamroom.toaster.model._toast_
import com.cranoxz.streamroom.util.getFileSize

@Composable
fun AppNavigations() {
    val navController = rememberNavController()
    val bread by _toast_.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "lobby") {
            composable(route = "lobby") {
                val viewmodel: Viewmodel = hiltViewModel()
                val name by viewmodel.partyName.collectAsStateWithLifecycle()
                val uri by viewmodel.uri.collectAsStateWithLifecycle()
                val onFileSelect =
                    rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { result ->
                        viewmodel.setVideoUri(result ?: return@rememberLauncherForActivityResult)
                    }

                LobbyContent(
                    partyName = name,
                    onNameChange = viewmodel::onNameChange,
                    onCreate = {
                        viewmodel.onPartyCreate {
                            navController.navigate(
                                ProcessEngine(
                                    partyname = name,
                                    uri = uri!!.toString()
                                )
                            )
                        }
                    },
                    fileUri = uri,
                    onFileSelect = {
                        onFileSelect.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.VideoOnly,
                            )
                        )
                    }
                )
            }

            composable<ProcessEngine> { stack ->
                val viewmodel = hiltViewModel<ProcessEngineModel>()
                val args = stack.toRoute<ProcessEngine>()
                val context = LocalContext.current
                val progress by progress.collectAsStateWithLifecycle()
                val state by viewmodel.state.collectAsStateWithLifecycle(IDLE)
                val uri = Uri.decode(args.uri).toUri()
                LaunchedEffect(Unit) {
                    viewmodel.createParty(
                        args.partyname,
                        filesize = getFileSize(context, uri),
                        uri = uri
                    )
                }
                ProcessContent(
                    state,
//                videoUri = uri,
                    progress = progress
                )
            }
        }

        Toaster(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp),
            bread
        )
    }
}