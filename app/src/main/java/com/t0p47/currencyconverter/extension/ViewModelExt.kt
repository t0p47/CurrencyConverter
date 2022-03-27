package com.t0p47.currencyconverter.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T: ViewModel> FragmentActivity.injectViewModel(factory: ViewModelProvider.Factory): T{
    return ViewModelProvider(this, factory)[T::class.java]
}

inline fun <reified T: ViewModel> Fragment.injectViewModel(factory: ViewModelProvider.Factory): T{
    return ViewModelProvider(this.requireActivity(), factory)[T::class.java]
}

inline fun <reified T: ViewModel> Fragment.injectSharedViewModel(factory: ViewModelProvider.Factory): T{
    return ViewModelProvider(this.requireActivity(), factory)[T::class.java]
}