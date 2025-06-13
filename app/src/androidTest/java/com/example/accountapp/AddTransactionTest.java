package com.example.accountapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddTransactionTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addTransactionTest() {
        // 1. 添加账户
        onView(withId(R.id.navigation_accounts)).perform(click());
        onView(withId(R.id.fabAddAccount)).perform(click());
        onView(withId(R.id.account_name_input)).perform(replaceText("现金"), closeSoftKeyboard());
        onView(withId(R.id.account_balance_input)).perform(replaceText("1000"), closeSoftKeyboard());
        onView(withId(R.id.account_note_input)).perform(replaceText("测试账户"), closeSoftKeyboard());
        onView(withId(R.id.account_type_spinner)).perform(click());
        onView(withText("现金")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.save_button)).perform(click());
        waitForViewToBeDisplayed(withText("现金"));

        // 2. 添加分类
        onView(withId(R.id.navigation_categories)).perform(click());
        onView(withId(R.id.fabAddCategory)).perform(click());
        onView(withId(R.id.editTextName)).perform(replaceText("餐饮"), closeSoftKeyboard());
        onView(withId(R.id.recyclerViewIcons))
            .perform(androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.buttonSave)).perform(click());
        waitForViewToBeDisplayed(withText("餐饮"));
        onView(withId(R.id.navigation_transactions)).perform(click());
        onView(withId(R.id.fabAddTransaction)).perform(click());
        onView(withId(R.id.buttonSave)).perform(click());
        onView(withId(R.id.editTextAmount)).perform(replaceText("88.88"));
        onView(withId(R.id.buttonSave)).perform(click());
        onView(withId(R.id.spinnerAccount)).perform(click());
        onView(withText("现金")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.buttonSave)).perform(click());
        onView(withId(R.id.editTextDescription)).perform(replaceText("测试账目"), closeSoftKeyboard());
        onView(withId(R.id.spinnerType)).perform(click());
        onView(withText("收入")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spinnerCategory)).perform(click());
        onView(withText("餐饮")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.buttonSave)).perform(click());
        waitForViewToBeDisplayed(withId(R.id.recyclerViewTransactions));
        onView(withId(R.id.recyclerViewTransactions))
            .perform(androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition(0));
        onView(allOf(withId(R.id.textViewAmount), withText("88.88")))
            .check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewAccount), withText("现金")))
            .check(matches(isDisplayed()));

    }

    public static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }
                CharSequence error = ((TextInputLayout) view).getError();
                if (error == null) return false;
                return expectedErrorText.equals(error.toString());
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("with TextInputLayout error text: " + expectedErrorText);
            }
        };
    }

    private void waitForViewToBeDisplayed(Matcher<View> matcher) {
    }
} 