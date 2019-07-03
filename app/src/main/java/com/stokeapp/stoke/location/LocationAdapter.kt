package com.stokeapp.stoke.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stokeapp.stoke.R
import kotlinx.android.synthetic.main.item_location.view.*

typealias OnLocationClickListener = ((LocationItem) -> Unit)

class LocationAdapter(
    private val onLocationClickListener: OnLocationClickListener
) : ListAdapter<LocationItem, LocationAdapter.ViewHolder>(LocationDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_location, parent, false)
        return ViewHolder(view, onLocationClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        itemView: View,
        private val onLocationClickListener: OnLocationClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: LocationItem) {
            itemView.locationText.text = item.location
            itemView.setOnClickListener {
                onLocationClickListener.invoke(item)
            }
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