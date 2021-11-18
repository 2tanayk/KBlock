package com.kamathtanay.kblock

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager

import android.app.NotificationChannel

import android.os.Build
import android.util.Log
import com.kamathtanay.kblock.ConstantsKBlock.CHANNEL_ID

class KBlockApp: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @SuppressLint("LongLogTag")
    private fun createNotificationChannel() {
        Log.e("createNotificationChannel","...")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val phoneStateServiceChannel = NotificationChannel(CHANNEL_ID, "KBlock Call Blocker Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(phoneStateServiceChannel)
        }
    }
}