package com.saba21.test

import android.os.Bundle
import android.util.Log
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
                itemView.tvValue.text = item.id.toString()
            }
            .checkItemIds { oldItem, newItem ->
                oldItem.id == newItem.id
            }.checkItemContent { oldItem, newItem ->
                oldItem == newItem
            }.onDataRequested { pageIndex ->
                Log.e("pageIndex", pageIndex.toString())
                pagingManager.setData(
                    pageIndex,
                    if (pageIndex < 100)
                        listOf(SimpleModel(System.currentTimeMillis()))
                    else
                        emptyList()
                )
            }
            .build()

        rvContent.adapter = pagingManager.getAdapter()
        rvContent.layoutManager = LinearLayoutManager(this)

    }


    class SimpleModel(val id: Long)
}
