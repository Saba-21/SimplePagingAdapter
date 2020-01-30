package com.saba21.test

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.saba21.simplepagingadapter.library.PagingManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.simple_item.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var pagingManager: PagingManager<SimpleModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagingManager = PagingManager
            .builder<SimpleModel>()
            .setLifecycle(this)
            .setPageSize(1)
            .setLayout(R.layout.simple_item)
            .onItemBind { itemView, position, item ->
                onBindItem(position, itemView, item)
            }.onReflectLoader { isVisible ->
                reflectLoader(isVisible)
            }.onReflectPlaceHolder { isVisible ->
                reflectPlaceHolder(isVisible)
            }.checkItemIds { oldItem, newItem ->
                oldItem.id == newItem.id
            }.checkItemContent { oldItem, newItem ->
                oldItem == newItem
            }.onDataRequested { pageIndex, lastItem ->
                loadData(pageIndex, lastItem)
            }.build()

        vLoader.setOnRefreshListener {
            pagingManager.refreshData()
        }

        rvContent.adapter = pagingManager.getAdapter()
        rvContent.layoutManager = LinearLayoutManager(this)

    }

    private fun onBindItem(
        position: Int,
        itemView: View,
        item: SimpleModel
    ) {
        itemView.tvValue.text = item.id.toString()
        itemView.setOnClickListener {
            pagingManager.updateItemAt(item.copy(id = item.id - 1000000), position)
        }
    }

    private fun reflectPlaceHolder(isVisible: Boolean) {
        tvPlaceHolder.visibility = if (isVisible)
            View.VISIBLE
        else
            View.GONE
    }

    private fun reflectLoader(isVisible: Boolean) {
        vLoader.isRefreshing = isVisible
    }

    private fun loadData(pageIndex: Int, lastItem: SimpleModel?) {
        Log.e("pageIndex", pageIndex.toString())
        Log.e("lastItem", lastItem?.id.toString())


        Handler().postDelayed({

            pagingManager.setData(
                pageIndex,
                if (pageIndex < 100)
                    listOf(SimpleModel(System.currentTimeMillis()))
                else
                    emptyList()
            )

        }, 500)
    }

    data class SimpleModel(val id: Long)

}