package com.diastore.util.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.setIsRefreshing(isRefreshing: Boolean) {
    setRefreshing(isRefreshing)
}