package com.example.servicehomework

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.servicehomework.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: TimerViewModel by lazy {
        TimerViewModel()
    }

    private val timeReceiver = object: BroadcastReceiver() {
        override fun onReceive(comtext: Context?, intent: Intent?) {
            viewModel.setReceive(intent?.getStringExtra("time").toString())
            Log.d("TAG", "onReceive  ${intent?.getStringExtra("time")}")
        }
    }


    private val adapter: LabAdapter by lazy {
        LabAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = LinearLayoutManager(this)

        setContentView(binding.root)
        setBroadcastReceiver()
        setStartButton()
        setStopButton()
        setResetButton()

        viewModel.time.observe(this) {
            binding.tvTime.text = it.time
        }

        viewModel.labTime.observe(this) {
            adapter.submitList(it)
        }

    }

    private fun setBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            timeReceiver, IntentFilter("TimerServiceFilter")
        )
        Log.d("TAG", "setBroadcastReceiver")
    }

    private fun setStartButton() {
        binding.btnStart.setOnClickListener {
            val intent = Intent(applicationContext, Timer::class.java)
            startService(intent)
        }
    }

    private fun setStopButton() {
        binding.btnStop.setOnClickListener {
            viewModel.setLabTime()
        }
    }

    private fun setResetButton() {
        binding.btnReset.setOnClickListener {
            val intent = Intent(applicationContext, Timer::class.java)
            stopService(intent)
            resetTimer()
        }
    }

    private fun resetTimer() {
       viewModel.removeAllTime()
    }

}