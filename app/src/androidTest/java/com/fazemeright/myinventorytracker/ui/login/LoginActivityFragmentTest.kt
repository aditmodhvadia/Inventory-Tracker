package com.fazemeright.myinventorytracker.ui.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import com.fazemeright.myinventorytracker.ui.base.BaseUiFragmentTest
import com.fazemeright.myinventorytracker.ui.itemlist.ItemListActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LoginActivityFragmentTest : BaseUiFragmentTest<LoginFragment>() {
    override fun getActivity(): Class<LoginFragment> = LoginFragment::class.java

    val VALID_EMAIL = "validtest@test.com"
    val VALID_PASSWORD = "12345678"
    val INVALID_PASSWORD = "1234567890"

    override fun allViewsAreDisplayed() {
        R.id.etEmail.isViewDisplayed()
        R.id.etPassword.isViewDisplayed()
        R.id.btnLogin.isViewDisplayed()
    }


    @Test
    fun validLogin() = runBlocking {
        delay(100)
        onView(withId(R.id.etEmail)).check(matches(isDisplayed())).perform(
            clearText(),
            typeText(
                VALID_EMAIL
            )
        )
        delay(100)
        onView(
            withId(R.id.etPassword)
        ).check(matches(isDisplayed())).perform(
            clearText(),
            typeText(
                VALID_PASSWORD
            ), closeSoftKeyboard()
        )
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed())).perform(click())
        delay(1000)
        intended(hasComponent(ItemListActivity::class.java.name))
    }

    @Test
    fun invalidLogin() = runBlocking {
        delay(100)
        onView(withId(R.id.etEmail)).check(matches(isDisplayed())).perform(
            clearText(),
            typeText(
                VALID_EMAIL
            )
        )
        delay(100)
        onView(
            withId(R.id.etPassword)
        ).check(matches(isDisplayed())).perform(
            clearText(),
            typeText(
                INVALID_PASSWORD
            ), closeSoftKeyboard()
        )
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed())).perform(click())
        delay(1000)
        onView(withId(R.id.btnLogin)).check(
            matches(isDisplayed())
        )
        assert(true)
    }


}