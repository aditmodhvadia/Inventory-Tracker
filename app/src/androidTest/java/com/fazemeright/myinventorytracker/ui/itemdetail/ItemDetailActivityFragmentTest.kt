package com.fazemeright.myinventorytracker.ui.itemdetail

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import com.fazemeright.myinventorytracker.isViewWithTextDisplayed
import com.fazemeright.myinventorytracker.ui.base.BaseUiActivityTest
import com.fazemeright.myinventorytracker.ui.base.BaseUiFragmentTest


class ItemDetailActivityFragmentTest : BaseUiFragmentTest<ItemDetailFragment>() {

    override fun allViewsAreDisplayed() {
        R.string.item_detail_title.isViewWithTextDisplayed()
        R.id.edtItemName.isViewDisplayed()
        R.id.tvBagname.isViewDisplayed()
        R.id.edtItemDesc.isViewDisplayed()
        R.id.edtItemQuantity.isViewDisplayed()
        R.id.action_delete_item.isViewDisplayed()

        onView(withContentDescription("Navigate up"))
            .check(matches(isDisplayed()))
    }

    override fun getFragment(): Class<ItemDetailFragment> = ItemDetailFragment::class.java
}
