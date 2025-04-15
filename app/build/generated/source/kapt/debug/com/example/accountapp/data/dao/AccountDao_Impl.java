package com.example.accountapp.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.accountapp.data.Converters;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.entity.AccountType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AccountDao_Impl implements AccountDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Account> __insertionAdapterOfAccount;

  private final EntityDeletionOrUpdateAdapter<Account> __deletionAdapterOfAccount;

  private final EntityDeletionOrUpdateAdapter<Account> __updateAdapterOfAccount;

  private final SharedSQLiteStatement __preparedStmtOfUpdateBalance;

  public AccountDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAccount = new EntityInsertionAdapter<Account>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `accounts` (`id`,`name`,`type`,`balance`,`note`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Account entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        final String _tmp = Converters.accountTypeToString(entity.getType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindDouble(4, entity.getBalance());
        if (entity.getNote() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNote());
        }
      }
    };
    this.__deletionAdapterOfAccount = new EntityDeletionOrUpdateAdapter<Account>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `accounts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Account entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAccount = new EntityDeletionOrUpdateAdapter<Account>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `accounts` SET `id` = ?,`name` = ?,`type` = ?,`balance` = ?,`note` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Account entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        final String _tmp = Converters.accountTypeToString(entity.getType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindDouble(4, entity.getBalance());
        if (entity.getNote() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNote());
        }
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateBalance = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Account account) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAccount.insert(account);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Account account) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAccount.handle(account);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Account account) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAccount.handle(account);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateBalance(final long accountId, final double amount) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateBalance.acquire();
    int _argIndex = 1;
    _stmt.bindDouble(_argIndex, amount);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, accountId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateBalance.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Account>> getAllAccounts() {
    final String _sql = "SELECT * FROM accounts ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"accounts"}, false, new Callable<List<Account>>() {
      @Override
      @Nullable
      public List<Account> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<Account> _result = new ArrayList<Account>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Account _item;
            _item = new Account();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final AccountType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromAccountType(_tmp);
            _item.setType(_tmpType);
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            _item.setBalance(_tmpBalance);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item.setNote(_tmpNote);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Account> getAccountById(final long id) {
    final String _sql = "SELECT * FROM accounts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"accounts"}, false, new Callable<Account>() {
      @Override
      @Nullable
      public Account call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final Account _result;
          if (_cursor.moveToFirst()) {
            _result = new Account();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final AccountType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromAccountType(_tmp);
            _result.setType(_tmpType);
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            _result.setBalance(_tmpBalance);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _result.setNote(_tmpNote);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
