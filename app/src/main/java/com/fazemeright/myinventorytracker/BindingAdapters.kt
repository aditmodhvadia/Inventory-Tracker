package com.fazemeright.myinventorytracker

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setBagName")
fun setBagName(textView: TextView, bagName: String) {
    textView.text = bagName
}
