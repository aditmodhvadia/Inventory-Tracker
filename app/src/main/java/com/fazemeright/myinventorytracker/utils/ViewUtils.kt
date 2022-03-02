package com.fazemeright.myinventorytracker.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.showToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.hideToolBar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
}

fun Fragment.showToolBar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
}

fun Activity.hideKeyboard() {
    val view: View? = this.currentFocus
    if (view != null) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            view.windowToken,
            0
        )
    }
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun <T : AppCompatActivity> AppCompatActivity.open(classType: Class<T>) {
    startActivity(Intent(this, classType))
}