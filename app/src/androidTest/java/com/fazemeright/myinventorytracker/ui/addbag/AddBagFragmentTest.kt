package com.fazemeright.myinventorytracker.ui.addbag

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import com.fazemeright.myinventorytracker.isViewWithTextDisplayed
import com.fazemeright.myinventorytracker.ui.base.BaseUiFragmentTest
import org.junit.Test

class AddBagFragmentTest : BaseUiFragmentTest<AddBagFragment>() {

    @Test
    override fun allViewsAreDisplayed() {
        R.string.add_bag_title.isViewWithTextDisplayed()
        R.id.edtBagName.isViewDisplayed()
        R.id.btnChooseColor.isViewDisplayed()
        R.id.viewBagColorDisplay.isViewDisplayed()
        R.id.edtBagDesc.isViewDisplayed()
        R.id.btnAddBag.isViewDisplayed()

        onView(withContentDescription("Navigate up")).check(matches(isDisplayed()))
    }

    @Test
    fun inputInAllFields() {
        onView(withId(R.id.edtBagName)).check(matches(isDisplayed()))
            .perform(typeText("Test Bag Name"))
        onView(withId(R.id.edtBagDesc)).check(matches(isDisplayed()))
            .perform(typeText("Test Bag Description"))
        onView(withId(R.id.btnChooseColor)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.cpv_hex)).check(matches(isDisplayed()))
            .perform(clearText(), typeText("ff0000"), closeSoftKeyboard())

//        Hit select button for picking color
        onView(withId(android.R.id.button1)).check(matches(isDisplayed())).perform(click())

        onView(withId(R.id.btnAddBag)).check(matches(isDisplayed())).perform(click())
    }

    override fun getFragment(): Class<AddBagFragment> = AddBagFragment::class.java
}