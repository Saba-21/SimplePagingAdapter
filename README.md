
# SimplePagingAdapter
Wrap of Google's [Paging library](https://developer.android.com/topic/libraries/architecture/paging)  making it easy to implement

## Result
<img height=480 width=270  src="https://github.com/Saba-21/SimplePagingAdapter/blob/master/test/src/main/res/raw/sample.gif"/>

<br>

## Setup
Paging component implementation is encapsulated in `PagingManager`
```
pagingManager = PagingManager
.builder<SimpleModel>()
.setLifecycle(this)
.setPageSize(10)
.setLayout(R.layout.simple_item)
.onItemBind { itemView, position, item ->
    onBindItem(position, itemView, item)
}.checkItemIds { oldItem, newItem ->
    oldItem.id == newItem.id
}.checkItemContent { oldItem, newItem ->
    oldItem == newItem
}.onDataRequested { pageIndex, lastItem ->
    loadData(pageIndex, lastItem)
}.build()
```

`PagingManager` has public method for getting adapter: `recyclerView.adapter = pagingManager.getAdapter()`

`PagingManager` is generic on data type

`setLifecycle` is to set lifecycle owner that is used by manager to observe component's `PagedList`

`setPageSize` initializes page size including initial page

`setLayout` is to set item's layout id

`onItemBind` is called on adapter's `onBindViewHolder`

`checkItemIds` and `checkItemContent` are used by `DiffUtil's` callback

## Loading data

`onDataRequested` is called every time when `DataSource's` callbacks are triggered,

there is passing two parameters:`pageIndex` ans `lastItem` for loading relevant data

after loading data `PagingManager` has public method `setData(pageIndex: Int, data: List<T>)` for populating corresponding page

## Additional setup

you can additionally set `.onReflectLoader { isVisible ->  }` and `.onReflectPlaceHolder { isVisible ->  }`,

`onReflectLoader` is called every time page starts loading

`onReflectPlaceHolder` is called if initial page is loaded empty

`PagingManager` also has two public methods for updating data

`pagingManager.refreshData()` to reset whole content

`pagingManager.updateItemAt(newItem: T, position: Int)` to update particular item at position

<br>

## Import in project via Gradle:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.Saba-21:SimplePagingAdapter:0.2'
    implementation 'androidx.paging:paging-runtime-ktx:2.1.1'
}
```

## Sample usage
A sample project which demonstrates sample usage is available in the `test` module.
