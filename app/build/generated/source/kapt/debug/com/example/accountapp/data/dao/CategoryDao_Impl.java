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
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.entity.TransactionEntity;
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
public final class CategoryDao_Impl implements CategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Category> __insertionAdapterOfCategory;

  private final EntityDeletionOrUpdateAdapter<Category> __deletionAdapterOfCategory;

  private final EntityDeletionOrUpdateAdapter<Category> __updateAdapterOfCategory;

  public CategoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCategory = new EntityInsertionAdapter<Category>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `categories` (`id`,`name`,`type`,`icon`,`color`,`parentId`,`level`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Category entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        final String _tmp = Converters.transactionTypeToString(entity.getType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        if (entity.getIcon() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getIcon());
        }
        if (entity.getColor() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getColor());
        }
        statement.bindLong(6, entity.getParentId());
        statement.bindLong(7, entity.getLevel());
      }
    };
    this.__deletionAdapterOfCategory = new EntityDeletionOrUpdateAdapter<Category>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `categories` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Category entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCategory = new EntityDeletionOrUpdateAdapter<Category>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `categories` SET `id` = ?,`name` = ?,`type` = ?,`icon` = ?,`color` = ?,`parentId` = ?,`level` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Category entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        final String _tmp = Converters.transactionTypeToString(entity.getType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        if (entity.getIcon() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getIcon());
        }
        if (entity.getColor() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getColor());
        }
        statement.bindLong(6, entity.getParentId());
        statement.bindLong(7, entity.getLevel());
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public void insert(final Category category) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCategory.insert(category);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Category category) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCategory.handle(category);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Category category) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCategory.handle(category);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Category>> getCategoriesByType(final TransactionEntity.Type type) {
    final String _sql = "SELECT * FROM categories WHERE type = ? ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = Converters.transactionTypeToString(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"categories"}, false, new Callable<List<Category>>() {
      @Override
      @Nullable
      public List<Category> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfParentId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentId");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final List<Category> _result = new ArrayList<Category>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Category _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final TransactionEntity.Type _tmpType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp_1);
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpParentId;
            _tmpParentId = _cursor.getLong(_cursorIndexOfParentId);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            _item = new Category(_tmpName,_tmpType,_tmpIcon,_tmpColor,_tmpParentId,_tmpLevel);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
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
  public LiveData<List<Category>> getCategoriesByLevel(final int level) {
    final String _sql = "SELECT * FROM categories WHERE level = ? ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, level);
    return __db.getInvalidationTracker().createLiveData(new String[] {"categories"}, false, new Callable<List<Category>>() {
      @Override
      @Nullable
      public List<Category> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfParentId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentId");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final List<Category> _result = new ArrayList<Category>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Category _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final TransactionEntity.Type _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp);
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpParentId;
            _tmpParentId = _cursor.getLong(_cursorIndexOfParentId);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            _item = new Category(_tmpName,_tmpType,_tmpIcon,_tmpColor,_tmpParentId,_tmpLevel);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
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
  public LiveData<List<Category>> getCategoriesByParentId(final long parentId) {
    final String _sql = "SELECT * FROM categories WHERE parentId = ? ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, parentId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"categories"}, false, new Callable<List<Category>>() {
      @Override
      @Nullable
      public List<Category> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfParentId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentId");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final List<Category> _result = new ArrayList<Category>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Category _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final TransactionEntity.Type _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp);
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpParentId;
            _tmpParentId = _cursor.getLong(_cursorIndexOfParentId);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            _item = new Category(_tmpName,_tmpType,_tmpIcon,_tmpColor,_tmpParentId,_tmpLevel);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
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
  public LiveData<List<Category>> getAllCategories() {
    final String _sql = "SELECT * FROM categories ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"categories"}, false, new Callable<List<Category>>() {
      @Override
      @Nullable
      public List<Category> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfParentId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentId");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final List<Category> _result = new ArrayList<Category>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Category _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final TransactionEntity.Type _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = Converters.fromTransactionType(_tmp);
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpParentId;
            _tmpParentId = _cursor.getLong(_cursorIndexOfParentId);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            _item = new Category(_tmpName,_tmpType,_tmpIcon,_tmpColor,_tmpParentId,_tmpLevel);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
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
  public Category getCategory(final long id) {
    final String _sql = "SELECT * FROM categories WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final int _cursorIndexOfParentId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentId");
      final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
      final Category _result;
      if (_cursor.moveToFirst()) {
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final TransactionEntity.Type _tmpType;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfType)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfType);
        }
        _tmpType = Converters.fromTransactionType(_tmp);
        final String _tmpIcon;
        if (_cursor.isNull(_cursorIndexOfIcon)) {
          _tmpIcon = null;
        } else {
          _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
        }
        final String _tmpColor;
        if (_cursor.isNull(_cursorIndexOfColor)) {
          _tmpColor = null;
        } else {
          _tmpColor = _cursor.getString(_cursorIndexOfColor);
        }
        final long _tmpParentId;
        _tmpParentId = _cursor.getLong(_cursorIndexOfParentId);
        final int _tmpLevel;
        _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
        _result = new Category(_tmpName,_tmpType,_tmpIcon,_tmpColor,_tmpParentId,_tmpLevel);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
