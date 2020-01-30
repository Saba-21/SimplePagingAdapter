package com.saba21.simplepagingadapter.library.dataLoading

import androidx.paging.PageKeyedDataSource

class SimpleDataSource<T>(
    private val requestPageData: (pageIndex: Int) -> Unit
) : PageKeyedDataSource<Int, T>() {

    private var initialCallback: LoadInitialCallback<Int, T>? = null
    private var updateCallback: LoadCallback<Int, T>? = null

    fun onDataLoaded(page: Int, data: List<T>) {
        if (page == 0) {
            initialCallback?.onResult(data, null, 1)
            initialCallback = null
        }
        if (page > 0) {
            updateCallback?.onResult(data, page + 1)
            updateCallback = null
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        initialCallback = callback
        requestPageData.invoke(0)
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, T>
    ) {
        updateCallback = callback
        requestPageData.invoke(params.key)
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, T>
    ) {
    }

}