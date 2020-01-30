package com.saba21.simplepagingadapter.library.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

class SimpleAdapter<T>(
    @LayoutRes
    private val layoutId: Int,
    private val onBind: (itemView: View, position: Int, item: T) -> Unit,
    diffUtilCallback: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, SimpleViewHolder>(diffUtilCallback) {

    private val dataSet = mutableListOf<T>()

    fun addData(data: List<T>) {
        dataSet.addAll(data)
    }

    fun clearData() {
        dataSet.clear()
    }

    fun onUpdateItem(newItem: T, position: Int) {
        dataSet.removeAt(position)
        dataSet.add(position, newItem)
        this.notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return SimpleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layoutId,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val itemFromPaging = getItem(position)
        val externalItem = dataSet.getOrNull(position)
        val item = externalItem ?: itemFromPaging
        if (item != null)
            onBind(holder.itemView, position, item)
    }

}