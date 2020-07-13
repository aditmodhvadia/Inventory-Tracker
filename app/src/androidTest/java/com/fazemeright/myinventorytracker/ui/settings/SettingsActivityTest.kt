package com.fazemeright.myinventorytracker.ui.settings

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import com.fazemeright.myinventorytracker.isViewWithTextDisplayed
import com.fazemeright.myinventorytracker.ui.base.BaseUiTest
import org.junit.Test

class SettingsActivityTest : BaseUiTest<SettingsActivity>() {

    @Test
    override fun allViewsAreDisplayed() {
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

    override fun getActivity(): Class<SettingsActivity> = SettingsActivity::class.java
}