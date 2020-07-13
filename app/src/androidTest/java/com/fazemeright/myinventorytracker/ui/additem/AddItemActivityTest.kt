package com.fazemeright.myinventorytracker.ui.additem

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import com.fazemeright.myinventorytracker.isViewWithTextDisplayed
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddItemActivityTest {

    @Before
    fun setUp() {
        Intents.init()
        ActivityScenario.launch(AddItemActivity::class.java)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun allViewsAreDisplayed() {
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
