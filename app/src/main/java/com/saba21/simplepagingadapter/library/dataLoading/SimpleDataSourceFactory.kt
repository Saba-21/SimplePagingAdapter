package com.saba21.simplepagingadapter.library.dataLoading

import androidx.paging.DataSource

class SimpleDataSourceFactory<T>(
    private val showLoader: () -> Unit,
    private val hideLoader: () -> Unit,
    private val showPlaceHolder: () -> Unit,
    private val hidePlaceHolder: () -> Unit,
    private val requestPageData: (pageIndex: Int, lastItem: T?) -> Unit
) : DataSource.Factory<Int, T>() {

    var currentDataSource: SimpleDataSource<T>? = null

    override fun create(): DataSource<Int, T> {
        currentDataSource = SimpleDataSource(
            requestPageData = requestPageData,
            showLoader = showLoader,
            hideLoader = hideLoader,
            showPlaceHolder = showPlaceHolder,
            hidePlaceHolder = hidePlaceHolder
        )
        return currentDataSource!!
    }

}