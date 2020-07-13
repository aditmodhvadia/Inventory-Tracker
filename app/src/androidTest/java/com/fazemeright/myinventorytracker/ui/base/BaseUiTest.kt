package com.fazemeright.myinventorytracker.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUiTest {
    @Before
    fun setUp() {
        Intents.init()
        ActivityScenario.launch(getActivity())
    }

    abstract fun getActivity(): Class<AppCompatActivity>

    @Test
    abstract fun allViewsAreDisplayed()

    @After
    fun tearDown() {
        Intents.release()
    }

}