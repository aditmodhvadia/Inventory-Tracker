package com.fazemeright.myinventorytracker.ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUiFragmentTest<A : Fragment> {
    @Before
    fun setUp() {
        Intents.init()
        FragmentScenario.launch(getFragment())
    }

    abstract fun getFragment(): Class<A>

    @Test
    abstract fun allViewsAreDisplayed()

    @After
    fun tearDown() {
        Intents.release()
    }

}
