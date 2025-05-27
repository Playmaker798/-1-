package com.example.accountapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.accountapp.R;
import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // 进入账户页面
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_accounts), withContentDescription("账户"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        // 点击添加账户按钮
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fabAddAccount), withContentDescription("添加账户"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        // 必填项校验：不输入名称直接保存
        onView(withId(R.id.account_name_input)).perform(replaceText(""));
        onView(withId(R.id.account_balance_input)).perform(replaceText("100"));
        onView(withId(R.id.save_button)).perform(click());
        onView(withId(R.id.layout_account_name)).check(matches(hasTextInputLayoutErrorText("Please enter account name")));

        // 必填项校验：不输入余额直接保存
        onView(withId(R.id.account_name_input)).perform(replaceText("测试账户"));
        onView(withId(R.id.account_balance_input)).perform(replaceText(""));
        onView(withId(R.id.save_button)).perform(click());
        onView(withId(R.id.layout_balance)).check(matches(hasTextInputLayoutErrorText("Please enter a valid balance")));

        // 输入完整信息，选择账户类型
        onView(withId(R.id.account_name_input)).perform(replaceText("测试账户"));
        onView(withId(R.id.account_balance_input)).perform(replaceText("100"));
        onView(withId(R.id.account_type_spinner)).perform(click());
        onView(withText("现金")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.save_button)).perform(click());
        // 如有Toast提示可加如下断言（需配合Toast测试工具类）
        // onView(withText("添加成功")).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        // 断言账户列表展示
        onView(withId(R.id.textViewAccountName)).check(matches(withText("测试账户")));
        onView(withId(R.id.textViewAccountType)).check(matches(withText("现金")));
        onView(withId(R.id.textViewBalance)).check(matches(withText("$100.00")));
    }

    // 新增：断言 TextInputLayout error 的自定义 Matcher
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
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
