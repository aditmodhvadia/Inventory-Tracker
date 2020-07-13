package com.fazemeright.myinventorytracker.ui.addbag

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import com.fazemeright.myinventorytracker.isViewWithTextDisplayed
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddBagActivityTest {

    @Before
    fun setUp() {
        Intents.init()
        ActivityScenario.launch(AddBagActivity::class.java)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun allViewsAreDisplayed() {
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
}


