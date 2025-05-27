package com.example.accountapp;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;

import com.example.accountapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.function.ThrowingRunnable;

import java.util.concurrent.TimeoutException;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.any;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        // 1. 新增账户
        onView(withId(R.id.navigation_accounts)).perform(click());
        onView(withId(R.id.fabAddAccount)).perform(click());
        onView(withId(R.id.account_name_input)).perform(replaceText("AA"), closeSoftKeyboard());
        onView(withId(R.id.account_balance_input)).perform(replaceText("111"), closeSoftKeyboard());
        onView(withId(R.id.account_note_input)).perform(replaceText("初始备注"), closeSoftKeyboard());
        onView(withId(R.id.account_type_spinner)).perform(click());
        onView(withText("储蓄卡")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.save_button)).perform(click());

        waitForViewToBeDisplayed(withId(R.id.recyclerViewAccounts));
        onView(withText("AA")).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerViewAccounts)).perform(actionOnItemAtPosition(0, click()));

        waitForViewToBeDisplayed(withId(R.id.layout_account_name));
        onView(withId(R.id.account_name_input)).check(matches(isDisplayed()));

        onView(withId(R.id.account_name_input)).check(matches(withEditTextContent("AA")));
        onView(withId(R.id.account_balance_input)).check(matches(withEditTextContent("111.0")));
        onView(withId(R.id.account_note_input)).check(matches(withEditTextContent("初始备注")));
        onView(withId(R.id.account_type_spinner)).check(matches(withSpinnerText(containsString("储蓄卡"))));

        onView(withId(R.id.account_name_input)).perform(replaceText("BB"), closeSoftKeyboard());
        onView(withId(R.id.account_balance_input)).perform(replaceText("222"), closeSoftKeyboard());
        onView(withId(R.id.account_note_input)).perform(replaceText("新备注"), closeSoftKeyboard());
        onView(withId(R.id.account_type_spinner)).perform(click());
        onView(withText("现金")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.save_button)).perform(click());

        waitForViewToBeDisplayed(withId(R.id.recyclerViewAccounts));
        onView(withText("BB")).check(matches(isDisplayed()));
        onView(withText("现金")).check(matches(isDisplayed()));
        onView(withText("$222.00")).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerViewAccounts)).perform(actionOnItemAtPosition(0, click()));

        waitForViewToBeDisplayed(withId(R.id.layout_account_name));
        onView(withId(R.id.account_name_input)).check(matches(isDisplayed()));

        onView(withId(R.id.account_name_input)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.account_name_input)).check(matches(isDisplayed()));
        onView(withId(R.id.layout_account_name)).check(matches(hasTextInputLayoutErrorText(containsString("Please enter account name"))));

        pressBack();

        waitForViewToBeDisplayed(withId(R.id.recyclerViewAccounts));

        onView(withId(R.id.recyclerViewAccounts)).perform(actionOnItemAtPosition(0, click()));

        waitForViewToBeDisplayed(withId(R.id.layout_account_name));
        onView(withId(R.id.account_balance_input)).check(matches(isDisplayed()));

        onView(withId(R.id.account_balance_input)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.account_balance_input)).check(matches(isDisplayed()));
        onView(withId(R.id.layout_balance)).check(matches(hasTextInputLayoutErrorText(containsString("Please enter a valid balance"))));

        pressBack();

        waitForViewToBeDisplayed(withId(R.id.recyclerViewAccounts));

        onView(withId(R.id.recyclerViewAccounts)).perform(actionOnItemAtPosition(0, click()));

        waitForViewToBeDisplayed(withId(R.id.layout_account_name));

        onView(withId(R.id.account_type_spinner)).perform(click());
        onView(withText("请选择")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.save_button)).perform(click());

        onView(withText("Please select account type"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()));

        pressBack();

        waitForViewToBeDisplayed(withId(R.id.recyclerViewAccounts));

        onView(withId(R.id.recyclerViewAccounts)).perform(actionOnItemAtPosition(0, click()));

        waitForViewToBeDisplayed(withId(R.id.layout_account_name));

        onView(withId(R.id.account_type_spinner)).perform(click());
        onView(withText("现金")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.save_button)).perform(click());

        waitForViewToBeDisplayed(withId(R.id.recyclerViewAccounts));
        onView(withText("现金")).check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }

    public static TypeSafeMatcher<View> hasTextInputLayoutErrorText(final Matcher<String> errorTextMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("TextInputLayout error text: ");
                errorTextMatcher.describeTo(description);
            }
            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof com.google.android.material.textfield.TextInputLayout)) {
                    return false;
                }
                CharSequence error = ((com.google.android.material.textfield.TextInputLayout) view).getError();
                return error != null && errorTextMatcher.matches(error.toString());
            }
        };
    }

    public static Matcher<View> withEditTextContent(final String expected) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof android.widget.EditText)) return false;
                CharSequence text = ((android.widget.EditText) view).getText();
                return text != null && text.toString().equals(expected);
            }
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("with EditText content: " + expected);
            }
        };
    }

    public static Matcher<View> withToastText(final String expectedText) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with toast text: " + expectedText);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextView)) {
                    return false;
                }
                String text = ((TextView) view).getText().toString();
                return expectedText.equals(text);
            }
        };
    }

    public static Matcher<View> withSpinnerText(final Matcher<String> textMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with spinner text: ");
                textMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof android.widget.Spinner)) {
                    return false;
                }
                android.widget.Spinner spinner = (android.widget.Spinner) view;
                Object selectedItem = spinner.getSelectedItem();
                return selectedItem != null && textMatcher.matches(selectedItem.toString());
            }
        };
    }

    private void waitForViewToBeDisplayed(final Matcher<View> viewMatcher) {
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + 15000; // 15 seconds timeout
        final long checkInterval = 100; // 100ms between checks

        while (System.currentTimeMillis() < endTime) {
            try {
                onView(viewMatcher).check(matches(isDisplayed()));
                return;
            } catch (Exception e) {
            }
            try {
                Thread.sleep(checkInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Timeout waiting for view to be displayed: " + viewMatcher.toString());
    }
}
