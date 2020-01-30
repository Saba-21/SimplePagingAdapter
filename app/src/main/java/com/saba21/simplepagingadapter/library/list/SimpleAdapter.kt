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
        val item = getItem(position)
        if (item != null)
            onBind(holder.itemView, position, item)
    }

}