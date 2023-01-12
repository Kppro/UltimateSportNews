package com.usn.ultimatesportnews

import android.app.Application
import com.usn.ultimatesportnews.data.local.DatabaseUsn
import com.usn.ultimatesportnews.data.network.ApiUsn
import timber.log.Timber

class UsnApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        Timber.plant(Timber.DebugTree())
        ApiUsn.init()
        DatabaseUsn.init(this)
    }

}