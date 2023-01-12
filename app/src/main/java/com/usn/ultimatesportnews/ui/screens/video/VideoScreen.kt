package com.usn.ultimatesportnews.ui.screens.video

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoScreen(videoUrl: String) {
    Scaffold(
        content = { innerPadding ->
            VideoView(videoUrl, Modifier.padding(innerPadding))
        },
        backgroundColor = MaterialTheme.colors.secondaryVariant
    )
}

@Composable
fun VideoView(videoUri: String, padding: Modifier) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(LocalContext.current)
        .build()
        .also { exoPlayer ->
            val mediaItem = MediaItem.Builder()
                .setUri(videoUri)
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.playWhenReady = true
            exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            exoPlayer.prepare()
        }

    DisposableEffect(
        AndroidView(factory = {
            StyledPlayerView(context).apply {
                hideController()
                useController = false
                player = exoPlayer

            }
        })
    ) {
        onDispose { exoPlayer.release() }
    }
}