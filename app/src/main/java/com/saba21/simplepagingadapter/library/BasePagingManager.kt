package com.saba21.simplepagingadapter.library

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.saba21.simplepagingadapter.library.dataLoading.SimpleDataSource
import com.saba21.simplepagingadapter.library.dataLoading.SimpleDataSourceFactory
import com.saba21.simplepagingadapter.library.list.SimpleAdapter

open class BasePagingManager<T> : DiffUtil.ItemCallback<T>() {

    protected var lifecycleOwner: LifecycleOwner? = null
    protected var layout: Int? = null
    protected var pageSize: Int? = null
    protected var onItemBind: ((itemView: View, position: Int, item: T) -> Unit)? = null
    protected var checkItemIds: ((oldItem: T, newItem: T) -> Boolean)? = null
    protected var checkItemContent: ((oldItem: T, newItem: T) -> Boolean)? = null
    protected var onDataRequested: ((pageIndex: Int) -> Unit)? = null

    protected var listAdapter: SimpleAdapter<T>? = null
    private var dataSource: SimpleDataSource<T>? = null
    private var dataSourceFactory: SimpleDataSourceFactory<T>? = null

    protected fun setUp() {
        listAdapter = SimpleAdapter(layout!!, onItemBind!!, this)

        val pageConfig = PagedList.Config
            .Builder()
            .setPageSize(pageSize!!)
            .setInitialLoadSizeHint(pageSize!!)
            .setEnablePlaceholders(false)
            .build()

        dataSourceFactory = SimpleDataSourceFactory(
            onDataRequested!!
        )

        val pageSubject = LivePagedListBuilder(dataSourceFactory!!, pageConfig).build()

        pageSubject.observe(lifecycleOwner!!, Observer {
            listAdapter!!.submitList(it)
        })

    }

    fun setData(pageIndex: Int, data: List<T>) {
        dataSourceFactory?.currentDataSource?.onDataLoaded(pageIndex, data)
    }

    override fun areItemsTheSame(oldItem: T, newItem: T)
            : Boolean = checkItemIds!!.invoke(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T)
            : Boolean = checkItemContent!!.invoke(oldItem, newItem)

}