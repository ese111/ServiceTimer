package com.example.servicehomework

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.servicehomework.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val _time = MutableStateFlow("")

    private val timeReceiver = object: BroadcastReceiver() {
        override fun onReceive(comtext: Context?, intent: Intent?) {
            _time.value = intent?.getStringExtra("time").toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBroadcastReceiver()
        startTimer()
    }

    private fun setBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            timeReceiver, IntentFilter("TimerServiceFilter")
        )
    }

    private fun startTimer() {
        binding.btnStart.setOnClickListener {
            val intent = Intent(applicationContext, Timer::class.java)
            startService(intent)
        }
    }

    private fun stopTimer() {
        binding.btnStop.setOnClickListener {

        }
    }

    private fun resetTimer() {
        binding.btnReset.setOnClickListener {
            val intent = Intent(applicationContext, Timer::class.java)
            stopService(intent)
        }
    }

}