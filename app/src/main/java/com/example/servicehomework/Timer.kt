package com.example.servicehomework

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Chronometer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.*

class Timer : Service() {

    private var time = 0

    private lateinit var timer: Job

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timer = CoroutineScope(Job() + Dispatchers.Default).launch {
            while (true) {
                delay(1000L)
                time++
                sendMessage()
                Log.d("TAG", "${time}")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun sendMessage() {
        val intent = Intent("TimerServiceFilter")
        intent.putExtra("time", "${time}초")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

}