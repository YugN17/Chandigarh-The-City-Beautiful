package com.chandigarhthecitybeautiful

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    @Test
    fun mainTest() {
        ActivityScenario.launch(MainActivity::class.java)
        Espresso.onData(
            Matchers.allOf(
                Matchers.instanceOf(TextView::class.java),
                Matchers.equalTo("Chandigarh The City Beautiful")
            )
        )
        Espresso.onData(
            Matchers.allOf(
                Matchers.instanceOf(TextView::class.java),
                Matchers.equalTo("Timing: 9AM - 9PM")
            )
        )
        onView(isRoot()).perform(waitFor(5000))
        val recyclerView = onView(
            Matchers.allOf(
                withId(R.id.rv_place_items),
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                    3
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        Espresso.onData(
            Matchers.allOf(
                Matchers.instanceOf(TextView::class.java),
                Matchers.equalTo("Garden of Fragrance")
            )
        )
        Espresso.onData(
            Matchers.allOf(
                Matchers.instanceOf(TextView::class.java),
                Matchers.equalTo("Garden of Fragrance")
            )
        )
        val appCompatImageButton = onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("Navigate up"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(delay)
            }

            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }
        }
    }
}