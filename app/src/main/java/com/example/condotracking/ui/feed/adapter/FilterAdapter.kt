package com.example.condotracking.ui.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.condotracking.R
import com.example.condotracking.ui.feed.data.FilterItem
import com.google.android.material.chip.Chip

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>(){

    var filterList = listOf<FilterItem>()
    var onChipClick: ((FilterItem) -> Unit)? = null

    fun setFilter(filters: List<FilterItem>) {
        filterList = filters
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chip_item, parent, false)
        return FilterViewHolder(itemView)
    }

    override fun getItemCount(): Int = filterList.size

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = filterList[position]
        holder.bind(item)

        holder.filter.setOnClickListener {
            onChipClick?.invoke(item)
        }
    }

    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filter: Chip = itemView.findViewById(R.id.chip)

        fun bind(item: FilterItem) {
            filter.text = item.name
            filter.isChecked = item.isSelected

            filter.setOnClickListener {
                item.isSelected = !item.isSelected
                filter.isChecked = item.isSelected

            }
        }

    }

}