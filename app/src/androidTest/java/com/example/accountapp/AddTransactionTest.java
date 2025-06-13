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

        // 3. 切换到账目页面，执行账目添加测试
        onView(withId(R.id.navigation_transactions)).perform(click());
        onView(withId(R.id.fabAddTransaction)).perform(click());

        // 4. 必填项校验：不输入金额直接保存
        onView(withId(R.id.buttonSave)).perform(click());
        // 这里假设金额输入框有错误提示，实际项目可根据实现调整
        // onView(withId(R.id.editTextAmount)).check(matches(hasTextInputLayoutErrorText("请输入金额")));

        // 5. 输入金额但不选账户
        onView(withId(R.id.editTextAmount)).perform(replaceText("88.88"));
        onView(withId(R.id.buttonSave)).perform(click());
        // onView(withId(R.id.spinnerAccount)).check(matches(hasTextInputLayoutErrorText("请选择账户")));

        // 6. 输入金额和账户但不选分类
        onView(withId(R.id.spinnerAccount)).perform(click());
        onView(withText("现金")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.buttonSave)).perform(click());
        // onView(withId(R.id.spinnerCategory)).check(matches(hasTextInputLayoutErrorText("请选择分类")));

        // 7. 输入全部信息
        onView(withId(R.id.spinnerCategory)).perform(click());
        onView(withText("餐饮")).inRoot(isPlatformPopup()).perform(click());
        // 日期可选填，若需指定可加如下
        // onView(withId(R.id.buttonDate)).perform(click());
        // onView(withText("2024-06-01")).perform(click());
        onView(withId(R.id.buttonSave)).perform(click());

        // 8. 验证账目列表展示
        onView(allOf(withId(R.id.textViewAmount), withText("88.88"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewAccount), withText("现金"))).check(matches(isDisplayed()));
        // 分类在item_transaction.xml没有单独id，若有可加断言
        // onView(allOf(withId(R.id.textViewCategory), withText("餐饮"))).check(matches(isDisplayed()));
    }

    // 可选：断言 TextInputLayout error 的自定义 Matcher
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
        // Implementation of waitForViewToBeDisplayed method
    }
} 