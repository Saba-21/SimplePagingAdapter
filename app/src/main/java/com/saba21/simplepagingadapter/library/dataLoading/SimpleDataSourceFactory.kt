package com.saba21.simplepagingadapter.library.dataLoading

import androidx.paging.DataSource

class SimpleDataSourceFactory<T>(
    private val requestPageData: (pageIndex: Int) -> Unit
) : DataSource.Factory<Int, T>() {

    var currentDataSource: SimpleDataSource<T>? = null

    override fun create(): DataSource<Int, T> {
        currentDataSource = SimpleDataSource(requestPageData = requestPageData)
        return currentDataSource!!
    }

}