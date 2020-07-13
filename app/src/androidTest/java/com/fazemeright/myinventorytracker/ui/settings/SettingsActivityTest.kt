package com.fazemeright.myinventorytracker.ui.settings

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsActivityTest {

    @Before
    fun setUp() {
        Intents.init()
        ActivityScenario.launch(SettingsActivity::class.java)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun allViewsAreDisplayed() {
        R.id.tvCopyright.isViewDisplayed()
        R.id.tvMadeWith.isViewDisplayed()
        R.id.settings.isViewDisplayed()

        onView(withText(R.string.display)).check(matches(isDisplayed()))
        onView(withText(R.string.theme)).check(matches(isDisplayed()))
        onView(withText(R.string.sync_header)).check(matches(isDisplayed()))
        onView(withText(R.string.sync_title)).check(matches(isDisplayed()))

        onView(withContentDescription("Navigate up"))
            .check(matches(isDisplayed()))
    }

}