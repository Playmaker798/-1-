package com.example.accountapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.widget.EditText;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddCategoryTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addCategoryTest() {
        onView(withId(R.id.navigation_categories)).perform(click());

        onView(withId(R.id.fabAddCategory)).perform(click());

        onView(withId(R.id.editTextName)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.buttonSave)).perform(click());
        onView(withId(R.id.editTextName)).check(matches(ErrorTextMatcher.withErrorText("Please enter category name")));

        onView(withId(R.id.editTextName)).perform(replaceText("餐饮"), closeSoftKeyboard());
        onView(withId(R.id.recyclerViewIcons)).perform(androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.buttonSave)).perform(click());
        onView(withText("餐饮")).check(matches(isDisplayed()));
    }

    public static class ErrorTextMatcher extends TypeSafeMatcher<View> {
        private final String expectedError;
        private ErrorTextMatcher(String expectedError) {
            this.expectedError = expectedError;
        }
        @Override
        protected boolean matchesSafely(View view) {
            if (!(view instanceof EditText)) return false;
            CharSequence error = ((EditText) view).getError();
            return error != null && error.toString().equals(expectedError);
        }
        @Override
        public void describeTo(Description description) {
            description.appendText("with error: " + expectedError);
        }
        public static Matcher<View> withErrorText(String expectedError) {
            return new ErrorTextMatcher(expectedError);
        }
    }
}