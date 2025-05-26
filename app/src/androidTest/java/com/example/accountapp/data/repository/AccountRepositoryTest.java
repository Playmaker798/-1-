package com.example.accountapp.data.repository;

import android.app.Application;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.accountapp.data.AppDatabase;
import com.example.accountapp.data.dao.AccountDao;
import com.example.accountapp.data.entity.Account;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@RunWith(AndroidJUnit4.class)
public class AccountRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private AppDatabase database;

    @Mock
    private AccountDao accountDao;

    private AccountRepository repository;
    private ExecutorService executorService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(database.accountDao()).thenReturn(accountDao);
        repository = new AccountRepository(ApplicationProvider.getApplicationContext());
        repository.db = database;
        repository.accountDao = accountDao;
        executorService = Executors.newSingleThreadExecutor();
    }

    @Test
    public void getAllAccounts_returnsAllAccounts() {
        // Arrange
        List<Account> accounts = Arrays.asList(
            new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金"),
            new Account("银行卡", Account.AccountType.BANK, 5000.0, "工资卡"),
            new Account("信用卡", Account.AccountType.CREDIT, -2000.0, "消费卡")
        );
        LiveData<List<Account>> liveData = new MutableLiveData<>(accounts);
        Mockito.when(accountDao.getAllAccounts()).thenReturn(liveData);

        // Act
        LiveData<List<Account>> result = repository.getAllAccounts();

        // Assert
        assertNotNull(result);
        assertEquals(accounts, result.getValue());
    }

    @Test
    public void getAccount_returnsAccount() {
        // Arrange
        Account expectedAccount = new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金");
        LiveData<Account> liveData = new MutableLiveData<>(expectedAccount);
        Mockito.when(accountDao.getAccountById(1)).thenReturn(liveData);

        // Act
        LiveData<Account> result = repository.getAccount(1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAccount, result.getValue());
    }

    @Test
    public void insert_callsDaoInsert() throws InterruptedException {
        // Arrange
        Account account = new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金");
        CountDownLatch latch = new CountDownLatch(1);
        
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                latch.countDown();
                return null;
            }
        }).when(accountDao).insert(any(Account.class));

        // Act
        repository.insert(account);
        boolean completed = latch.await(1, TimeUnit.SECONDS);

        // Assert
        assertNotNull("Operation did not complete in time", completed);
        Mockito.verify(accountDao).insert(account);
    }

    @Test
    public void update_callsDaoUpdate() throws InterruptedException {
        // Arrange
        Account account = new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金");
        CountDownLatch latch = new CountDownLatch(1);
        
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                latch.countDown();
                return null;
            }
        }).when(accountDao).update(any(Account.class));

        // Act
        repository.update(account);
        boolean completed = latch.await(1, TimeUnit.SECONDS);

        // Assert
        assertNotNull("Operation did not complete in time", completed);
        Mockito.verify(accountDao).update(account);
    }

    @Test
    public void delete_callsDaoDelete() throws InterruptedException {
        // Arrange
        Account account = new Account("现金账户", Account.AccountType.CASH, 1000.0, "日常现金");
        CountDownLatch latch = new CountDownLatch(1);
        
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                latch.countDown();
                return null;
            }
        }).when(accountDao).delete(any(Account.class));

        // Act
        repository.delete(account);
        boolean completed = latch.await(1, TimeUnit.SECONDS);

        // Assert
        assertNotNull("Operation did not complete in time", completed);
        Mockito.verify(accountDao).delete(account);
    }
} 