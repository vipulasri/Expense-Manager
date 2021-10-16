package com.vipulasri.expensemanager.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Vipul Asri on 16/10/21.
 */

abstract class BaseVM : ViewModel() {
    protected fun <T> LiveData<T>.setValue(value: T) {
        if (this is MutableLiveData<T>) setValue(value)
        else error("Not a mutable live data.")
    }
}