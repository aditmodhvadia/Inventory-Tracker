package com.fazemeright.myinventorytracker.ui.settings

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

        R.string.display.isViewWithTextDisplayed()
        R.string.theme.isViewWithTextDisplayed()
        R.string.sync_header.isViewWithTextDisplayed()
        R.string.sync_title.isViewWithTextDisplayed()

        onView(withContentDescription("Navigate up"))
            .check(matches(isDisplayed()))
    }

}