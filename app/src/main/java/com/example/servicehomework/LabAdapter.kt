package com.example.servicehomework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.servicehomework.databinding.ItemLabBinding

class LabAdapter: ListAdapter<LabTime, LabAdapter.LabViewHolder>(LabDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabViewHolder {
        return LabViewHolder(ItemLabBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LabViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LabViewHolder(private val binding: ItemLabBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(time: LabTime){
            binding.tvLabTime.text = time.time
        }
    }

    private object LabDiffUtil:DiffUtil.ItemCallback<LabTime>() {
        override fun areItemsTheSame(oldItem: LabTime, newItem: LabTime) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: LabTime, newItem: LabTime) =
            oldItem == newItem

    }



}