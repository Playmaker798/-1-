package com.example.accountapp.ui.accounts;

import android.app.Application;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.repository.AccountRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class AccountsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private AccountRepository repository;

    private AccountsViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // 创建一个AccountsViewModel的spy对象，并传入Application上下文
        viewModel = Mockito.spy(new AccountsViewModel(ApplicationProvider.getApplicationContext()));
        viewModel.repository = repository;
    }
    // 测试获取所有账户的方法
    @Test
    public void getAllAccounts_returnsAllAccounts() {
        // 准备期望的账户列表
        List<Account> expectedAccounts = Arrays.asList(
            new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金"),
            new Account("银行卡", Account.AccountType.BANK, 5000.0, "工资卡"),
            new Account("信用卡", Account.AccountType.CREDIT, -2000.0, "消费卡")
        );
        // 创建一个包含期望账户列表的LiveData对象
        LiveData<List<Account>> liveData = new MutableLiveData<>(expectedAccounts);
        Mockito.when(repository.getAllAccounts()).thenReturn(liveData);

        // 调用viewModel的getAllAccounts方法获取结果
        LiveData<List<Account>> result = viewModel.getAllAccounts();

        // 断言结果不为空且与期望的账户列表相等
        assertNotNull(result);
        assertEquals(expectedAccounts, result.getValue());
    }
    // 测试获取单个账户的方法
    @Test
    public void getAccount_returnsCorrectAccount() {
        // 准备期望的账户对象
        Account expectedAccount = new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金");
        LiveData<Account> liveData = new MutableLiveData<>(expectedAccount);
        Mockito.when(repository.getAccount(1)).thenReturn(liveData);

        LiveData<Account> result = viewModel.getAccount(1);

        // 断言结果不为空且与期望的账户对象相等
        assertNotNull(result);
        assertEquals(expectedAccount, result.getValue());
    }









/*
    // 测试插入账户的方法
    @Test
    public void insertAccount_savesAccount() {
        Account account = new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金");

        viewModel.insert(account);

        Mockito.verify(repository).insert(account);
    }
    // 测试更新账户的方法
    @Test
    public void updateAccount_updatesAccount() {
        // Arrange
        Account account = new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金");

        // Act
        viewModel.update(account);

        // Assert
        Mockito.verify(repository).update(account);
    }
    // 测试删除账户的方法
    @Test
    public void deleteAccount_deletesAccount() {
        // Arrange
        Account account = new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金");

        // Act
        viewModel.deleteAccount(account);

        // Assert
        Mockito.verify(repository).delete(account);
    }*/
}