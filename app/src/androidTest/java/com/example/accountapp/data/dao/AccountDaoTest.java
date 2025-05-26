package com.example.accountapp.data.dao;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.accountapp.data.AppDatabase;
import com.example.accountapp.data.entity.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class AccountDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase database;
    private AccountDao accountDao;
    private Account testAccount;
    private long testAccountId;

    @Before
    public void createDb() throws InterruptedException {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        accountDao = database.accountDao();
        
        // 创建测试账户
        testAccount = new Account("测试账户", Account.AccountType.CASH, 1000.0, "测试描述");
        accountDao.insert(testAccount);
        
        // 获取插入后的账户ID
        List<Account> accounts = getValue(accountDao.getAllAccounts());
        assertNotNull(accounts);
        assertTrue(accounts.size() > 0);
        testAccountId = accounts.get(0).getId();
        testAccount.setId(testAccountId);
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insertAndGetAccount() throws InterruptedException {
        // 获取所有账户
        List<Account> accounts = getValue(accountDao.getAllAccounts());
        assertNotNull(accounts);
        assertTrue(accounts.size() > 0);
        
        // 验证插入的账户
        Account insertedAccount = accounts.get(0);
        assertEquals(testAccount.getName(), insertedAccount.getName());
        assertEquals(testAccount.getType(), insertedAccount.getType());
        assertEquals(testAccount.getBalance(), insertedAccount.getBalance(), 0.001);
        assertEquals(testAccount.getNote(), insertedAccount.getNote());
    }

    @Test
    public void updateAccount() throws InterruptedException {
        // 首先验证原始余额
        List<Account> accountsBefore = getValue(accountDao.getAllAccounts());
        assertNotNull(accountsBefore);
        assertTrue(accountsBefore.size() > 0);
        assertEquals(1000.0, accountsBefore.get(0).getBalance(), 0.001);
        
        // 更新账户
        Account accountToUpdate = accountsBefore.get(0);
        accountToUpdate.setBalance(2000.0);
        accountDao.update(accountToUpdate);
        
        // 获取更新后的账户
        List<Account> accountsAfter = getValue(accountDao.getAllAccounts());
        assertNotNull(accountsAfter);
        assertTrue(accountsAfter.size() > 0);
        
        Account updatedAccount = accountsAfter.get(0);
        assertEquals("Balance should be updated to 2000.0", 2000.0, updatedAccount.getBalance(), 0.001);
        
        // 通过ID获取账户并验证更新
        Account accountById = getValue(accountDao.getAccountById(accountToUpdate.getId()));
        assertNotNull(accountById);
        assertEquals("Account retrieved by ID should have updated balance", 2000.0, accountById.getBalance(), 0.001);
    }

    @Test
    public void deleteAccount() throws InterruptedException {
        // 首先验证账户存在
        List<Account> accountsBefore = getValue(accountDao.getAllAccounts());
        assertNotNull(accountsBefore);
        assertTrue("Account should exist before deletion", accountsBefore.size() > 0);
        
        // 删除账户
        accountDao.delete(testAccount);
        
        // 获取所有账户并验证删除
        List<Account> accountsAfter = getValue(accountDao.getAllAccounts());
        assertNotNull(accountsAfter);
        assertTrue("Account list should be empty after deletion", accountsAfter.isEmpty());
        
        // 尝试获取已删除的账户
        Account deletedAccount = getValue(accountDao.getAccountById(testAccountId));
        assertTrue("Deleted account should not be found", deletedAccount == null);
    }

    @Test
    public void getAccountById() throws InterruptedException {
        // 获取特定账户
        Account account = getValue(accountDao.getAccountById(testAccountId));
        assertNotNull("Account should be found by ID", account);
        assertEquals("Account name should match", testAccount.getName(), account.getName());
        assertEquals("Account type should match", testAccount.getType(), account.getType());
        assertEquals("Account balance should match", testAccount.getBalance(), account.getBalance(), 0.001);
        assertEquals("Account note should match", testAccount.getNote(), account.getNote());
    }

    private <T> T getValue(LiveData<T> liveData) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        T[] data = (T[]) new Object[1];
        liveData.observeForever(value -> {
            data[0] = value;
            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS);
        return data[0];
    }
} 