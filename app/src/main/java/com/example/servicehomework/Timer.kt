package com.example.servicehomework

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Chronometer
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
                if (time != 0 && time % 8 == 0) {
                    createNotificationChannel()
                    showNotification()
                }
            }
        }

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun showNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, "timer")
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setContentTitle("타이머 알림")
            .setContentText("${time}초 경과하였습니다.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    private fun sendMessage() {
        val intent = Intent("TimerServiceFilter")
        intent.putExtra("time", "${time}초")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "timer"
            val descriptionText = "시간 경과 알림"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("timer", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
