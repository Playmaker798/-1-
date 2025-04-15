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
import com.example.accountapp.data.TransactionType;
import com.example.accountapp.data.entity.Budget;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BudgetDao_Impl implements BudgetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Budget> __insertionAdapterOfBudget;

  private final EntityDeletionOrUpdateAdapter<Budget> __deletionAdapterOfBudget;

  private final EntityDeletionOrUpdateAdapter<Budget> __updateAdapterOfBudget;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSpent;

  public BudgetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBudget = new EntityInsertionAdapter<Budget>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `budgets` (`id`,`categoryId`,`categoryName`,`amount`,`spent`,`month`,`year`,`type`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Budget entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        if (entity.getCategoryName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCategoryName());
        }
        statement.bindDouble(4, entity.getAmount());
        statement.bindDouble(5, entity.getSpent());
        statement.bindLong(6, entity.getMonth());
        statement.bindLong(7, entity.getYear());
        if (entity.getType() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, __TransactionType_enumToString(entity.getType()));
        }
      }
    };
    this.__deletionAdapterOfBudget = new EntityDeletionOrUpdateAdapter<Budget>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `budgets` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Budget entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfBudget = new EntityDeletionOrUpdateAdapter<Budget>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `budgets` SET `id` = ?,`categoryId` = ?,`categoryName` = ?,`amount` = ?,`spent` = ?,`month` = ?,`year` = ?,`type` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Budget entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        if (entity.getCategoryName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCategoryName());
        }
        statement.bindDouble(4, entity.getAmount());
        statement.bindDouble(5, entity.getSpent());
        statement.bindLong(6, entity.getMonth());
        statement.bindLong(7, entity.getYear());
        if (entity.getType() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, __TransactionType_enumToString(entity.getType()));
        }
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateSpent = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE budgets SET spent = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Budget budget) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBudget.insert(budget);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Budget budget) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfBudget.handle(budget);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Budget budget) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfBudget.handle(budget);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateSpent(final long id, final double spent) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSpent.acquire();
    int _argIndex = 1;
    _stmt.bindDouble(_argIndex, spent);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateSpent.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Budget>> getBudgetsByMonthAndYear(final int month, final int year) {
    final String _sql = "SELECT * FROM budgets WHERE month = ? AND year = ? ORDER BY categoryName";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, month);
    _argIndex = 2;
    _statement.bindLong(_argIndex, year);
    return __db.getInvalidationTracker().createLiveData(new String[] {"budgets"}, false, new Callable<List<Budget>>() {
      @Override
      @Nullable
      public List<Budget> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCategoryName = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "spent");
          final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<Budget> _result = new ArrayList<Budget>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Budget _item;
            _item = new Budget();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            _item.setCategoryId(_tmpCategoryId);
            final String _tmpCategoryName;
            if (_cursor.isNull(_cursorIndexOfCategoryName)) {
              _tmpCategoryName = null;
            } else {
              _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            }
            _item.setCategoryName(_tmpCategoryName);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final double _tmpSpent;
            _tmpSpent = _cursor.getDouble(_cursorIndexOfSpent);
            _item.setSpent(_tmpSpent);
            final int _tmpMonth;
            _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
            _item.setMonth(_tmpMonth);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            _item.setYear(_tmpYear);
            final TransactionType _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            }
            _item.setType(_tmpType);
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
  public LiveData<Budget> getBudgetById(final long id) {
    final String _sql = "SELECT * FROM budgets WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"budgets"}, false, new Callable<Budget>() {
      @Override
      @Nullable
      public Budget call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCategoryName = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "spent");
          final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final Budget _result;
          if (_cursor.moveToFirst()) {
            _result = new Budget();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            _result.setCategoryId(_tmpCategoryId);
            final String _tmpCategoryName;
            if (_cursor.isNull(_cursorIndexOfCategoryName)) {
              _tmpCategoryName = null;
            } else {
              _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            }
            _result.setCategoryName(_tmpCategoryName);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            _result.setAmount(_tmpAmount);
            final double _tmpSpent;
            _tmpSpent = _cursor.getDouble(_cursorIndexOfSpent);
            _result.setSpent(_tmpSpent);
            final int _tmpMonth;
            _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
            _result.setMonth(_tmpMonth);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            _result.setYear(_tmpYear);
            final TransactionType _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            }
            _result.setType(_tmpType);
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

  private String __TransactionType_enumToString(@NonNull final TransactionType _value) {
    switch (_value) {
      case INCOME: return "INCOME";
      case EXPENSE: return "EXPENSE";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private TransactionType __TransactionType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "INCOME": return TransactionType.INCOME;
      case "EXPENSE": return TransactionType.EXPENSE;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
