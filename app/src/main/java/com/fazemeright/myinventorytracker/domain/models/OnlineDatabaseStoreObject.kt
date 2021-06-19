package com.fazemeright.myinventorytracker.domain.models

/**
 * Interface that defines how a model is stored in online database
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
interface OnlineDatabaseStoreObject {
    /**
     * Get online database id of the object
     *
     * @return id for online database store
     */
    fun getOnlineDatabaseStoreId(): String?

    /**
     * Set the [onlineId] of the object for online database store
     *
     * @param onlineId online id of the object
     */
    fun setOnlineDatabaseStoreId(onlineId: String)
}
