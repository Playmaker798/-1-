package com.example.accountapp;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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

import com.example.accountapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anyOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        onView(withId(R.id.navigation_accounts)).perform(click());
        
        onView(withId(R.id.fabAddAccount)).perform(click());
        
        ViewInteraction textInputEditText = onView(
        allOf(withId(R.id.account_name_input),
        childAtPosition(
        childAtPosition(
        withId(R.id.layout_account_name),
        0),
        0),
        isDisplayed()));
        textInputEditText.perform(replaceText("AA"), closeSoftKeyboard());
        
        ViewInteraction textInputEditText2 = onView(
        allOf(withId(R.id.account_balance_input),
        childAtPosition(
        childAtPosition(
        withId(R.id.layout_balance),
        0),
        0),
        isDisplayed()));
        textInputEditText2.perform(replaceText("111"), closeSoftKeyboard());
        
        ViewInteraction textInputEditText3 = onView(
        allOf(withId(R.id.account_note_input),
        childAtPosition(
        childAtPosition(
        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
        0),
        0),
        isDisplayed()));
        textInputEditText3.perform(replaceText(""), closeSoftKeyboard());
        
        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.recyclerViewAccounts)).check(matches(isDisplayed()));
        
        ViewInteraction recyclerView = onView(
        allOf(withId(R.id.recyclerViewAccounts),
        childAtPosition(
        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
        0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        
        ViewInteraction editText = onView(
        allOf(withId(R.id.account_name_input), withText("AA"),
        withParent(withParent(withId(R.id.layout_account_name))),
        isDisplayed()));
        editText.check(matches(withText("AA")));

        onView(withId(R.id.account_type_spinner)).perform(click());
        onView(withText("储蓄卡")).inRoot(isPlatformPopup()).perform(click());

        onView(withId(R.id.account_type_spinner))
            .check(matches(withSpinnerText(org.hamcrest.Matchers.containsString("储蓄卡"))));
        
        ViewInteraction editText2 = onView(
        allOf(withId(R.id.account_balance_input), withText("111.0"),
        withParent(withParent(withId(R.id.layout_balance))),
        isDisplayed()));
        editText2.check(matches(withText("111.0")));
        
        ViewInteraction editText3 = onView(
        allOf(withId(R.id.account_note_input), withText(""),
        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
        isDisplayed()));
        editText3.check(matches(withText("")));
        
        ViewInteraction editText4 = onView(
        allOf(withId(R.id.account_note_input), withText(""),
        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
        isDisplayed()));
        editText4.check(matches(withText("")));
        
        ViewInteraction textInputEditText4 = onView(
        allOf(withId(R.id.account_balance_input), withText("111.0"),
        childAtPosition(
        childAtPosition(
        withId(R.id.layout_balance),
        0),
        0),
        isDisplayed()));
        textInputEditText4.perform(replaceText(""));
        
        ViewInteraction textInputEditText5 = onView(
        allOf(withId(R.id.account_balance_input),
        childAtPosition(
        childAtPosition(
        withId(R.id.layout_balance),
        0),
        0),
        isDisplayed()));
        textInputEditText5.perform(closeSoftKeyboard());
        
        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.layout_balance)).check(matches(hasTextInputLayoutErrorText("请输入有效的余额")));
        
        ViewInteraction textInputEditText6 = onView(
        allOf(withId(R.id.account_name_input), withText("AA"),
        childAtPosition(
        childAtPosition(
        withId(R.id.layout_account_name),
        0),
        0),
        isDisplayed()));
        textInputEditText6.perform(replaceText(""));
        
        ViewInteraction textInputEditText7 = onView(
        allOf(withId(R.id.account_name_input),
        childAtPosition(
        childAtPosition(
        withId(R.id.layout_account_name),
        0),
        0),
        isDisplayed()));
        textInputEditText7.perform(closeSoftKeyboard());
        
        ViewInteraction textInputEditText8 = onView(
        allOf(withId(R.id.account_balance_input),
        childAtPosition(
        childAtPosition(
        withId(R.id.layout_balance),
        0),
        0),
        isDisplayed()));
        textInputEditText8.perform(replaceText("123"), closeSoftKeyboard());
        
        onView(withId(R.id.save_button)).perform(click());
        onView(isRoot()).perform(closeSoftKeyboard());
        try {
            onView(withId(R.id.account_name_input)).check(matches(isDisplayed()));
            onView(withId(R.id.layout_account_name)).check((view, noViewFoundException) -> {
                if (view instanceof com.google.android.material.textfield.TextInputLayout) {
                    CharSequence error = ((com.google.android.material.textfield.TextInputLayout) view).getError();
                    System.out.println("账户名错误提示: " + error);
                }
            });
            onView(withId(R.id.layout_balance)).check((view, noViewFoundException) -> {
                if (view instanceof com.google.android.material.textfield.TextInputLayout) {
                    CharSequence error = ((com.google.android.material.textfield.TextInputLayout) view).getError();
                    System.out.println("余额错误提示: " + error);
                }
            });
            throw new AssertionError("保存失败，未能关闭对话框，请检查错误提示内容。");
        } catch (androidx.test.espresso.NoMatchingViewException e) {
            onView(withId(R.id.recyclerViewAccounts)).check(matches(isDisplayed()));
            onView(allOf(withId(R.id.textViewAccountName), withText("BB"),
                withParent(withParent(IsInstanceOf.<View>instanceOf(androidx.cardview.widget.CardView.class))),
                isDisplayed()))
                .check(matches(withText("BB")));
            onView(allOf(withId(R.id.textViewAccountType), withText("储蓄卡"),
                withParent(withParent(IsInstanceOf.<View>instanceOf(androidx.cardview.widget.CardView.class))),
                isDisplayed()))
                .check(matches(withText("储蓄卡")));
            onView(allOf(withId(R.id.textViewBalance), withText("$1234.00"),
                withParent(withParent(IsInstanceOf.<View>instanceOf(androidx.cardview.widget.CardView.class))),
                isDisplayed()))
                .check(matches(withText("$1234.00")));
        }
        return;
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

    public static TypeSafeMatcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("TextInputLayout error text: " + expectedErrorText);
            }
            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof com.google.android.material.textfield.TextInputLayout)) {
                    return false;
                }
                CharSequence error = ((com.google.android.material.textfield.TextInputLayout) view).getError();
                if (error == null) return false;
                return expectedErrorText.contentEquals(error);
            }
        };
    }
}
