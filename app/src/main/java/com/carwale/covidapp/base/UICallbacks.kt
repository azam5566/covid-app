package com.carwale.covidapp.base

interface UICallbacks<V> {

    fun getLayoutId(): Int
    fun getViewModel(): Class<V>
    fun onBinding()
    fun viewModelListener()
}