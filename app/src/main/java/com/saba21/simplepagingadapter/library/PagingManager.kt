package com.saba21.simplepagingadapter.library

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.saba21.simplepagingadapter.library.list.SimpleAdapter
import java.lang.ref.WeakReference

class PagingManager<T> : BasePagingManager<T>() {

    companion object {
        fun <P> builder() = PagingManager<P>()
    }

    fun setLifecycle(lifecycleOwner: LifecycleOwner): PagingManager<T> {
        this.lifecycleOwner = WeakReference(lifecycleOwner)
        return this
    }

    fun setLayout(layout: Int): PagingManager<T> {
        this.layout = layout
        return this
    }

    fun setPageSize(pageSize: Int): PagingManager<T> {
        this.pageSize = pageSize
        return this
    }

    fun onItemBind(onItemBind: (itemView: View, position: Int, item: T) -> Unit): PagingManager<T> {
        this.onItemBind = onItemBind
        return this
    }

    fun checkItemIds(checkItemIds: (oldItem: T, newItem: T) -> Boolean): PagingManager<T> {
        this.checkItemIds = checkItemIds
        return this
    }

    fun checkItemContent(checkItemContent: (oldItem: T, newItem: T) -> Boolean): PagingManager<T> {
        this.checkItemContent = checkItemContent
        return this
    }

    fun onDataRequested(onDataRequested: (pageIndex: Int, lastItem: T?) -> Unit): PagingManager<T> {
        this.onDataRequested = onDataRequested
        return this
    }

    fun onReflectLoader(onReflectLoader: (isVisible: Boolean) -> Unit): PagingManager<T> {
        this.onReflectLoader = onReflectLoader
        return this
    }

    fun onReflectPlaceHolder(onReflectPlaceHolder: (isVisible: Boolean) -> Unit): PagingManager<T> {
        this.onReflectPlaceHolder = onReflectPlaceHolder
        return this
    }

    fun getAdapter(): SimpleAdapter<T> {
        if (listAdapter == null)
            throw RuntimeException("should not call PagingManager.getAdapter() before PagingManager.build()")
        return listAdapter!!
    }

    fun build(): PagingManager<T> {
        when {
            lifecycleOwner == null -> {
                throw RuntimeException("forgot setLifecycle")
            }
            layout == null -> {
                throw RuntimeException("forgot setLayout")
            }
            pageSize == null -> {
                throw RuntimeException("forgot setPageSize")
            }
            onItemBind == null -> {
                throw RuntimeException("forgot onItemBind")
            }
            checkItemIds == null -> {
                throw RuntimeException("forgot checkItemIds")
            }
            checkItemContent == null -> {
                throw RuntimeException("forgot checkItemContent")
            }
            onDataRequested == null -> {
                throw RuntimeException("forgot onDataRequested")
            }
        }
        setUp()
        return this
    }

}