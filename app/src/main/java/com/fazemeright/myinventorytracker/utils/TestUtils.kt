package com.fazemeright.myinventorytracker.utils

import android.graphics.Color
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import kotlin.random.Random

object TestUtils {

    /**
     * Get a Bag Item with the given Id
     */
    fun getBagItem(id: Int, color: Int = Color.parseColor(getRandomColor())): BagItem {
        return BagItem(
            id,
            "Test Bag $id",
            bagColor = color,
        )
    }

    /**
     * Get a Inventory Item with the given Id
     */
    fun getInventoryItem(
        id: Int,
        bagId: Int,
        itemName: String = "Item $id inside bag $bagId"
    ): InventoryItem {
        return InventoryItem(
            itemId = id,
            itemName = itemName,
            bagOwnerId = bagId,
        )
    }

    /**
     * Get a random color in Hex format
     */
    fun getRandomColor(): String {
        val random = Random
        val letters =
            arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
        val colorBuilder = StringBuilder().append("#")
        repeat(6) {
            colorBuilder.append(letters[random.nextInt(letters.size)])
        }
        return colorBuilder.toString()
    }
}