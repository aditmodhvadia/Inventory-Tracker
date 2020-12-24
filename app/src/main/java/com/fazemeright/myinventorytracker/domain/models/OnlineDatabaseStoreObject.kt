package com.fazemeright.myinventorytracker.domain.models

interface OnlineDatabaseStoreObject {
    fun getOnlineDatabaseStoreId(): String?

    fun setOnlineDatabaseStoreId(onlineId: String)
}
