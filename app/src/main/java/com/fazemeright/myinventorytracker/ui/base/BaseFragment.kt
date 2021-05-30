package com.fazemeright.myinventorytracker.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

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
        binding = getViewBinding()
        binding.lifecycleOwner = this

        return binding.root
    }

    protected fun Fragment.showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun hideToolBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    fun showToolBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}