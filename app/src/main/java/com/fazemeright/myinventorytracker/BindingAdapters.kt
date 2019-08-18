package com.fazemeright.myinventorytracker

import android.graphics.Color
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:bgColor")
fun updateBackgroundColor(cardView: CardView, bagName: String) {
    cardView.setBackgroundColor(
        when (bagName) {
            "Red" -> Color.parseColor("#C92214")
            "Black AT" -> Color.parseColor("#000000")
            else -> Color.parseColor("#0A3D62")
        }
    )
}

@BindingAdapter("app:setBagName")
fun setBagName(textView: TextView, bagName: String) {
    textView.text = bagName
}