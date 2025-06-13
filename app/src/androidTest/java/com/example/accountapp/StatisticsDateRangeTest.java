package com.example.accountapp;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import androidx.test.espresso.Root;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Collection;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StatisticsDateRangeTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class, true, false);  // Don't launch activity automatically

    private void disableAnimations() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            android.content.Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.WINDOW_ANIMATION_SCALE, 0);

            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.TRANSITION_ANIMATION_SCALE, 0);

            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.ANIMATOR_DURATION_SCALE, 0);
        });
    }

    @Before
    public void setUp() {
        disableAnimations();

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        waitForActivityToBeResumed();
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    private void waitForActivityToBeResumed() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
            if (resumedActivities.isEmpty()) {
                throw new IllegalStateException("No activities in RESUMED stage");
            }
        });
    }

    private void waitForView(int viewId) {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(viewId)).check(matches(isDisplayed()));
    }

    @Test
    public void testStatistics() throws InterruptedException {
        // 1. 添加多个账户
        addAccount("现金账户", "1000", "现金");
        addAccount("储蓄卡", "2000", "储蓄卡");

        // 2. 添加多个分类
        addCategory("餐饮");
        addCategory("购物");
        addCategory("交通");
        addCategory("工资");

        // 3. 添加交易记录
        // 现金账户的交易
        addTransaction("100", "午餐", "现金账户", "支出", "餐饮", 2024, 5, 1);
        addTransaction("150", "晚餐", "现金账户", "支出", "餐饮", 2024, 5, 15);
        addTransaction("1000", "工资", "现金账户", "收入", "工资", 2024, 5, 1);
        
        // 储蓄卡账户的交易
        addTransaction("300", "衣服", "储蓄卡", "支出", "购物", 2024, 5, 10);
        addTransaction("200", "日用品", "储蓄卡", "支出", "购物", 2024, 5, 20);
        addTransaction("500", "奖金", "储蓄卡", "收入", "工资", 2024, 5, 15);
        addTransaction("50", "公交", "储蓄卡", "支出", "交通", 2024, 5, 5);
        addTransaction("80", "打车", "储蓄卡", "支出", "交通", 2024, 5, 25);

        // 4. 进入统计页面
        onView(withId(R.id.navigation_statistics)).perform(click());

        // 5. 设置日期范围：2024-05-01 ~ 2024-05-31
        onView(withId(R.id.buttonDateRange)).perform(click());
        onView(withClassName(Matchers.equalTo(android.widget.DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 5, 1));
        onView(withText("确定")).perform(click());
        onView(withClassName(Matchers.equalTo(android.widget.DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 5, 31));
        onView(withText("确定")).perform(click());

        // 6. 测试现金账户的统计
        onView(withId(R.id.spinnerAccount)).perform(click());
        onData(hasToString("现金账户")).inRoot(isPlatformPopup()).perform(click());
        
        // 测试全部类型
        onView(withId(R.id.spinnerType)).perform(click());
        onData(hasToString("全部")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textViewIncome)).check(matches(withText("¥1000.00")));
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥250.00")));
        onView(withId(R.id.textViewBalance)).check(matches(withText("¥750.00")));

        // 测试收入类型
        onView(withId(R.id.spinnerType)).perform(click());
        onData(hasToString("收入")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textViewIncome)).check(matches(withText("¥1000.00")));
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥0.00")));
        onView(withId(R.id.textViewBalance)).check(matches(withText("¥1000.00")));

        // 测试支出类型
        onView(withId(R.id.spinnerType)).perform(click());
        onData(hasToString("支出")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textViewIncome)).check(matches(withText("¥0.00")));
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥250.00")));
        onView(withId(R.id.textViewBalance)).check(matches(withText("¥-250.00")));

        // 7. 测试储蓄卡账户的统计
        onView(withId(R.id.spinnerAccount)).perform(click());
        onData(hasToString("储蓄卡")).inRoot(isPlatformPopup()).perform(click());
        
        // 测试全部类型
        onView(withId(R.id.spinnerType)).perform(click());
        onData(hasToString("全部")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textViewIncome)).check(matches(withText("¥500.00")));
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥630.00")));
        onView(withId(R.id.textViewBalance)).check(matches(withText("¥-130.00")));

        // 测试收入类型
        onView(withId(R.id.spinnerType)).perform(click());
        onData(hasToString("收入")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textViewIncome)).check(matches(withText("¥500.00")));
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥0.00")));
        onView(withId(R.id.textViewBalance)).check(matches(withText("¥500.00")));

        // 测试支出类型
        onView(withId(R.id.spinnerType)).perform(click());
        onData(hasToString("支出")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textViewIncome)).check(matches(withText("¥0.00")));
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥630.00")));
        onView(withId(R.id.textViewBalance)).check(matches(withText("¥-630.00")));

        // 8. 测试分类统计（以储蓄卡账户为例）
        // 测试购物分类
        onView(withId(R.id.spinnerCategory)).perform(click());
        onData(hasToString("购物")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥500.00")));

        // 测试交通分类
        onView(withId(R.id.spinnerCategory)).perform(click());
        onData(hasToString("交通")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥130.00")));

        // 重置分类过滤器为"全部"
        onView(withId(R.id.spinnerCategory)).perform(click());
        onData(is(nullValue())).inRoot(isPlatformPopup()).perform(click());

        // 9. 测试不同日期范围
        // 测试5月上半月：2024-05-01 ~ 2024-05-15
        onView(withId(R.id.buttonDateRange)).perform(click());
        onView(withClassName(Matchers.equalTo(android.widget.DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 5, 1));
        onView(withText("确定")).perform(click());
        onView(withClassName(Matchers.equalTo(android.widget.DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 5, 15));
        onView(withText("确定")).perform(click());

        // 检查储蓄卡账户在这个时间段的支出统计
        onView(withId(R.id.spinnerType)).perform(click());
        onData(hasToString("支出")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥350.00"))); // 300(衣服) + 50(公交)

        // 测试5月下半月：2024-05-16 ~ 2024-05-31
        onView(withId(R.id.buttonDateRange)).perform(click());
        onView(withClassName(Matchers.equalTo(android.widget.DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 5, 16));
        onView(withText("确定")).perform(click());
        onView(withClassName(Matchers.equalTo(android.widget.DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 5, 31));
        onView(withText("确定")).perform(click());

        // 检查储蓄卡账户在这个时间段的支出统计
        onView(withId(R.id.textViewExpense)).check(matches(withText("¥280.00"))); // 200(日用品) + 80(打车)
    }

    private void addTransaction(String amount, String desc, String account, String type, String category, int year, int month, int day) throws InterruptedException {
        waitForActivityToBeResumed();
        onView(withId(R.id.navigation_transactions)).perform(click());
        waitForView(R.id.fabAddTransaction);
        onView(withId(R.id.fabAddTransaction)).perform(click());
        
        // 等待输入框显示
        waitForView(R.id.editTextAmount);
        onView(withId(R.id.editTextAmount)).perform(replaceText(amount), closeSoftKeyboard());
        onView(withId(R.id.editTextDescription)).perform(replaceText(desc), closeSoftKeyboard());
        
        // 选择账户
        onView(withId(R.id.spinnerAccount)).perform(click());
        onData(hasToString(account)).inRoot(isPlatformPopup()).perform(click());
        
        // 选择类型
        onView(withId(R.id.spinnerType)).perform(click());
        onData(hasToString(type)).inRoot(isPlatformPopup()).perform(click());
        
        // 选择分类
        onView(withId(R.id.spinnerCategory)).perform(click());
        onData(hasToString(category)).inRoot(isPlatformPopup()).perform(click());
        
        // 选择日期
        onView(withId(R.id.editTextDate)).perform(click());
        onView(withClassName(Matchers.equalTo(android.widget.DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month, day));
        onView(withText("确定")).perform(click());
        
        // 保存
        onView(withId(R.id.buttonSave)).perform(click());
        onView(isRoot()).perform(waitFor(1000));
    }

    private void addAccount(String name, String balance, String type) throws InterruptedException {
        waitForActivityToBeResumed();
        onView(withId(R.id.navigation_accounts)).perform(click());
        waitForView(R.id.fabAddAccount);
        onView(withId(R.id.fabAddAccount)).perform(click());
        
        waitForView(R.id.account_name_input);
        onView(withId(R.id.account_name_input)).perform(replaceText(name), closeSoftKeyboard());
        onView(withId(R.id.account_balance_input)).perform(replaceText(balance), closeSoftKeyboard());
        onView(withId(R.id.account_note_input)).perform(replaceText("测试账户"), closeSoftKeyboard());
        onView(withId(R.id.account_type_spinner)).perform(click());
        onData(hasToString(type)).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.save_button)).perform(click());
        onView(isRoot()).perform(waitFor(1000));
    }

    private void addCategory(String name) throws InterruptedException {
        waitForActivityToBeResumed();
        onView(withId(R.id.navigation_categories)).perform(click());
        waitForView(R.id.fabAddCategory);
        onView(withId(R.id.fabAddCategory)).perform(click());
        
        waitForView(R.id.editTextName);
        onView(withId(R.id.editTextName)).perform(replaceText(name), closeSoftKeyboard());
        onView(withId(R.id.recyclerViewIcons))
                .perform(androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.buttonSave)).perform(click());
        onView(isRoot()).perform(waitFor(1000));
    }
}