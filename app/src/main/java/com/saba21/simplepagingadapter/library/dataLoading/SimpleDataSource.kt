package com.saba21.simplepagingadapter.library.dataLoading

import androidx.paging.PageKeyedDataSource

class SimpleDataSource<T>(
    private val showLoader: () -> Unit,
    private val hideLoader: () -> Unit,
    private val showPlaceHolder: () -> Unit,
    private val hidePlaceHolder: () -> Unit,
    private val requestPageData: (pageIndex: Int, lastItem: T?) -> Unit
) : PageKeyedDataSource<Int, T>() {

    private var initialCallback: LoadInitialCallback<Int, T>? = null
    private var updateCallback: LoadCallback<Int, T>? = null
    private var lastItem: T? = null

    fun onDataLoaded(page: Int, data: List<T>) {
        commonDataHandler(data, isFirstPage = page == 0)
        if (page == 0) {
            initialCallback?.onResult(data, null, 1)
            initialCallback = null
        }
        if (page > 0) {
            updateCallback?.onResult(data, page + 1)
            updateCallback = null
        }
    }

    private fun commonDataHandler(
        data: List<T>,
        isFirstPage: Boolean = false
    ) {
        lastItem = data.lastOrNull()
        hideLoader.invoke()
        if (data.isEmpty() && isFirstPage)
            showPlaceHolder.invoke()
        else
            hidePlaceHolder.invoke()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        showLoader.invoke()
        initialCallback = callback
        requestPageData.invoke(0, lastItem)
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, T>
    ) {
        showLoader.invoke()
        updateCallback = callback
        requestPageData.invoke(params.key, lastItem)
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, T>
    ) {
    }

}