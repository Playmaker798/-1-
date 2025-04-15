package com.example.accountapp.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.accountapp.data.Converters;
import com.example.accountapp.data.entity.TransactionEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TransactionEntity> __insertionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __deletionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __updateAdapterOfTransactionEntity;

  public TransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransactionEntity = new EntityInsertionAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `transactions` (`id`,`type`,`accountId`,`categoryId`,`amount`,`description`,`date`,`note`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = Converters.transactionTypeToString(entity.getType());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        statement.bindLong(3, entity.getAccountId());
        statement.bindLong(4, entity.getCategoryId());
        statement.bindDouble(5, entity.getAmount());
        if (entity.getDescription() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDescription());
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getDate());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_1);
        }
        if (entity.getNote() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNote());
        }
      }
    };
    this.__deletionAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `transactions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transactions` SET `id` = ?,`type` = ?,`accountId` = ?,`categoryId` = ?,`amount` = ?,`description` = ?,`date` = ?,`note` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = Converters.transactionTypeToString(entity.getType());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        statement.bindLong(3, entity.getAccountId());
        statement.bindLong(4, entity.getCategoryId());
        statement.bindDouble(5, entity.getAmount());
        if (entity.getDescription() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDescription());
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getDate());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_1);
        }
        if (entity.getNote() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNote());
        }
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public void insert(final TransactionEntity transaction) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTransactionEntity.insert(transaction);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final TransactionEntity transaction) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTransactionEntity.handle(transaction);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final TransactionEntity transaction) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTransactionEntity.handle(transaction);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<TransactionEntity>> getAllTransactions() {
    final String _sql = "SELECT * FROM transactions ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<TransactionEntity>>() {
      @Override
      @Nullable
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            _item = new TransactionEntity();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final TransactionEntity.Type _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp);
            _item.setType(_tmpType);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            _item.setAccountId(_tmpAccountId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            _item.setCategoryId(_tmpCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = Converters.fromTimestamp(_tmp_1);
            _item.setDate(_tmpDate);
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
  public LiveData<TransactionEntity> getTransactionById(final long id) {
    final String _sql = "SELECT * FROM transactions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<TransactionEntity>() {
      @Override
      @Nullable
      public TransactionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final TransactionEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new TransactionEntity();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final TransactionEntity.Type _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp);
            _result.setType(_tmpType);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            _result.setAccountId(_tmpAccountId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            _result.setCategoryId(_tmpCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            _result.setAmount(_tmpAmount);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _result.setDescription(_tmpDescription);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = Converters.fromTimestamp(_tmp_1);
            _result.setDate(_tmpDate);
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

  @Override
  public LiveData<List<TransactionEntity>> getTransactionsByAccount(final long accountId) {
    final String _sql = "SELECT * FROM transactions WHERE accountId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<TransactionEntity>>() {
      @Override
      @Nullable
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            _item = new TransactionEntity();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final TransactionEntity.Type _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp);
            _item.setType(_tmpType);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            _item.setAccountId(_tmpAccountId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            _item.setCategoryId(_tmpCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = Converters.fromTimestamp(_tmp_1);
            _item.setDate(_tmpDate);
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
  public LiveData<List<TransactionEntity>> getTransactionsByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM transactions WHERE categoryId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<TransactionEntity>>() {
      @Override
      @Nullable
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            _item = new TransactionEntity();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final TransactionEntity.Type _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp);
            _item.setType(_tmpType);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            _item.setAccountId(_tmpAccountId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            _item.setCategoryId(_tmpCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = Converters.fromTimestamp(_tmp_1);
            _item.setDate(_tmpDate);
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
  public LiveData<List<TransactionEntity>> getTransactionsByType(
      final TransactionEntity.Type type) {
    final String _sql = "SELECT * FROM transactions WHERE type = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = Converters.transactionTypeToString(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<TransactionEntity>>() {
      @Override
      @Nullable
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            _item = new TransactionEntity();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final TransactionEntity.Type _tmpType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp_1);
            _item.setType(_tmpType);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            _item.setAccountId(_tmpAccountId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            _item.setCategoryId(_tmpCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = Converters.fromTimestamp(_tmp_2);
            _item.setDate(_tmpDate);
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
  public LiveData<List<TransactionEntity>> getTransactionsByDateRange(final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM transactions WHERE date BETWEEN ? AND ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = Converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = Converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<TransactionEntity>>() {
      @Override
      @Nullable
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            _item = new TransactionEntity();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final TransactionEntity.Type _tmpType;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp_2);
            _item.setType(_tmpType);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            _item.setAccountId(_tmpAccountId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            _item.setCategoryId(_tmpCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final Date _tmpDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = Converters.fromTimestamp(_tmp_3);
            _item.setDate(_tmpDate);
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
  public LiveData<Double> getTotalByTypeAndDateRange(final TransactionEntity.Type type,
      final Date startDate, final Date endDate) {
    final String _sql = "SELECT SUM(amount) FROM transactions WHERE type = ? AND date BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    final String _tmp = Converters.transactionTypeToString(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = Converters.dateToTimestamp(startDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    _argIndex = 3;
    final Long _tmp_2 = Converters.dateToTimestamp(endDate);
    if (_tmp_2 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_2);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp_3;
            if (_cursor.isNull(0)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getDouble(0);
            }
            _result = _tmp_3;
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

  @Override
  public LiveData<Double> getTotalByAccountAndTypeAndDateRange(final long accountId,
      final TransactionEntity.Type type, final Date startDate, final Date endDate) {
    final String _sql = "SELECT SUM(amount) FROM transactions WHERE accountId = ? AND type = ? AND date BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    _argIndex = 2;
    final String _tmp = Converters.transactionTypeToString(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = Converters.dateToTimestamp(startDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    _argIndex = 4;
    final Long _tmp_2 = Converters.dateToTimestamp(endDate);
    if (_tmp_2 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_2);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp_3;
            if (_cursor.isNull(0)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getDouble(0);
            }
            _result = _tmp_3;
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
