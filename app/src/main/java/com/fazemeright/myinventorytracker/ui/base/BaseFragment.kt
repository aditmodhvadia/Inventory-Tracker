package com.fazemeright.myinventorytracker.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

@Deprecated("Can't use with Hilt anymore.", level = DeprecationLevel.ERROR)
abstract class BaseFragment<V : ViewDataBinding> :
    Fragment() {

    lateinit var binding: V
    abstract fun getViewBinding(): V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return getViewBinding().apply {

        }.root
    }
}