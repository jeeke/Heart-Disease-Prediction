package me.gyanesh.hdp.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> buildFactory(crossinline factory: () -> T): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
}

inline fun <reified T : ViewModel> FragmentActivity.createViewModel(
    crossinline factory: () -> T
): T {
    return ViewModelProvider(this, buildFactory(factory))[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.createViewModel(
    crossinline factory: () -> T
): T {
    return ViewModelProvider(this, buildFactory(factory))[T::class.java]
}