package com.fazemeright.myinventorytracker.ui.base

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ActivityContext

abstract class BaseViewModel constructor(@ActivityContext private val context: Context) :
    ViewModel() {

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }
}