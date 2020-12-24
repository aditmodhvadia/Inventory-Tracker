package com.fazemeright.myinventorytracker.firebase.models

interface OnlineDatabaseStoreObject {
    fun getOnlineDatabaseStoreId(): String?

    fun setOnlineDatabaseStoreId(onlineId: String)
}
