package com.example.assignment.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.assignment.R
import org.junit.Test

class AssignmentActivityTest {

    @Test
    fun isActivityView() {
        ActivityScenario.launch(AssignmentActivity::class.java)
        onView(withId(R.id.frmContainer)).check(matches(isDisplayed()))
    }
}