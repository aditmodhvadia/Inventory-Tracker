package com.fazemeright.myinventorytracker.ui.additem

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import com.fazemeright.myinventorytracker.isViewWithTextDisplayed
import com.fazemeright.myinventorytracker.ui.base.BaseUiTest
import org.junit.Test

class AddItemActivityTest : BaseUiTest<AddItemActivity>() {

    override fun getActivity(): Class<AddItemActivity> = AddItemActivity::class.java

    @Test
    override fun allViewsAreDisplayed() {
        R.id.edtItemName.isViewDisplayed()
        R.id.spinnerBag.isViewDisplayed()
        R.id.edtItemDesc.isViewDisplayed()
        R.id.edtItemQuantity.isViewDisplayed()
        R.id.action_add_item.isViewDisplayed()
        R.string.add_item_title.isViewWithTextDisplayed()

        onView(withContentDescription("Navigate up"))
            .check(matches(isDisplayed()))
    }
}
