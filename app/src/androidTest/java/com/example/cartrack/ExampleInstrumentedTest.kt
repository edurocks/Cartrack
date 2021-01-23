package com.example.cartrack

import android.widget.EditText
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cartrack.ui.main.users.UsersListFragment
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.cartrack", appContext.packageName)
    }

    @Test
    fun launchAppCorrectly() {
        ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun isLoginFragmentInView() {
        ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun isLoginSuccess() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(
            allOf(isDescendantOfA(withId(R.id.username)), isAssignableFrom(EditText::class.java)))
                  .perform(typeText("eduardo")
            , ViewActions.closeSoftKeyboard())
        onView(allOf(isDescendantOfA(withId(R.id.password)), isAssignableFrom(EditText::class.java)))
            .perform(typeText("vascodagama31")
            , ViewActions.closeSoftKeyboard())
        onView(allOf(isDescendantOfA(withId(R.id.country)), isAssignableFrom(EditText::class.java)))
            .perform(typeText("Portugal")
            , ViewActions.closeSoftKeyboard())

        onView(withId(R.id.login)).perform(click())

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)

        // Create a graphical FragmentScenario for the TitleScreen
        val titleScenario = launchFragmentInContainer<UsersListFragment>()

        // Set the NavController property on the fragment
        titleScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

    }
}