package com.usn.ultimatesportnews.ui.screens.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class VideoActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val videoUrl = intent?.getStringExtra("video_url")

        setContent {
            videoUrl?.let {
                VideoScreen(it)
            }
        }
    }
}

fun Context.startVideoActivity(context: Context, url: String) {
    val intent = Intent(context, VideoActivity::class.java)
    intent.putExtra("video_url", url)
    startActivity(intent)
}