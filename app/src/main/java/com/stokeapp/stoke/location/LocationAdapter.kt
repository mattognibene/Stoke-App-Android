package com.stokeapp.stoke.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stokeapp.stoke.R
import kotlinx.android.synthetic.main.item_location.view.*

class LocationAdapter :
        ListAdapter<LocationItem, LocationAdapter.ViewHolder>(LocationDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: LocationItem) {
            itemView.locationText.text = item.location
        }
    }

    class LocationDiffCallback : DiffUtil.ItemCallback<LocationItem>() {
        override fun areItemsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
            return oldItem.location == newItem.location
        }

        override fun areContentsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
            return oldItem == newItem
        }
    }
}