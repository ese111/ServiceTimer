package com.example.servicehomework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel: ViewModel() {

    private val _time = MutableLiveData<LabTime>()
    val time: LiveData<LabTime> = _time

    private val _labTime = MutableLiveData<List<LabTime>>()
    val labTime: LiveData<List<LabTime>> = _labTime

    private var id = 0

    fun setReceive(time: String) {
        _time.value = LabTime(time = time)
    }

    fun setLabTime() {
        val list = mutableListOf<LabTime>()
        _labTime.value?.let { labTime -> list.addAll(labTime) }
        _time.value?.let { time -> list.add(LabTime(id, time.time)) }
        _labTime.value = list
        id++
    }

    fun removeAllTime() {
        _time.value = LabTime()
        _labTime.value = emptyList()
    }
}