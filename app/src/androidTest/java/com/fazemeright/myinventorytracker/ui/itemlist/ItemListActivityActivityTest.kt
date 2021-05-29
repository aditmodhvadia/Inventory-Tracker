package com.fazemeright.myinventorytracker.ui.itemlist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import com.fazemeright.myinventorytracker.isViewWithTextDisplayed
import com.fazemeright.myinventorytracker.ui.addbag.AddBagActivity
import com.fazemeright.myinventorytracker.ui.additem.AddItemActivity
import com.fazemeright.myinventorytracker.ui.base.BaseUiActivityTest
import org.junit.Test

class ItemListActivityActivityTest : BaseUiActivityTest<ItemListActivity>() {

    override fun getActivity(): Class<ItemListActivity> = ItemListActivity::class.java

    @Test
    override fun allViewsAreDisplayed() {
        R.id.fabAddItem.isViewDisplayed()
        R.id.item_list.isViewDisplayed()
        R.id.action_search.isViewDisplayed()

        R.string.app_name.isViewWithTextDisplayed()

        onView(withContentDescription("More options")).check(matches(isDisplayed()))
            .perform(click())

        R.string.add_bag.isViewWithTextDisplayed()
        R.string.settings.isViewWithTextDisplayed()
    }

    @Test
    fun onClickFab() {
        onView(withId(R.id.fabAddItem)).check(matches(isDisplayed())).perform(click())

        intended(hasComponent(AddItemActivity::class.java.name))
    }

    @Test
    fun onClickAddBag() {
        onView(withContentDescription("More options")).check(matches(isDisplayed()))
            .perform(click())
        onView(withText(R.string.add_bag)).check(matches(isDisplayed()))
            .perform(click())
        intended(hasComponent(AddBagActivity::class.java.name))
    }

    @Test
    fun onAddItemSuccessfully() {
        onView(withId(R.id.fabAddItem)).check(matches(isDisplayed())).perform(click())
        intended(hasComponent(AddItemActivity::class.java.name))

        onView(withId(R.id.edtItemName)).check(matches(isDisplayed()))
            .perform(typeText("Test Item"), closeSoftKeyboard())

        onView(withId(R.id.action_add_item)).check(matches(isDisplayed())).perform(click())

        onView(withId(R.id.fabAddItem)).check(matches(isDisplayed()))
    }
}
