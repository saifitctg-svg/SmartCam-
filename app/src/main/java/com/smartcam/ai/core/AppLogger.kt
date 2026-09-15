package com.smartcam.ai.core

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

interface AppLogger {
    fun debug(message: String)
    fun error(message: String, throwable: Throwable? = null)
}

@Singleton
class AndroidAppLogger @Inject constructor() : AppLogger {
    override fun debug(message: String) {
        Log.d(TAG, message)
    }

    override fun error(message: String, throwable: Throwable?) {
        Log.e(TAG, message, throwable)
    }

    private companion object {
        const val TAG = "SmartCamAI"
    }
}
