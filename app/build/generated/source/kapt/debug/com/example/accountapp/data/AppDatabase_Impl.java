package com.example.accountapp.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.accountapp.data.dao.AccountDao;
import com.example.accountapp.data.dao.AccountDao_Impl;
import com.example.accountapp.data.dao.BudgetDao;
import com.example.accountapp.data.dao.BudgetDao_Impl;
import com.example.accountapp.data.dao.CategoryDao;
import com.example.accountapp.data.dao.CategoryDao_Impl;
import com.example.accountapp.data.dao.TransactionDao;
import com.example.accountapp.data.dao.TransactionDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AccountDao _accountDao;

  private volatile CategoryDao _categoryDao;

  private volatile TransactionDao _transactionDao;

  private volatile BudgetDao _budgetDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `accounts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `type` TEXT, `balance` REAL NOT NULL, `note` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `type` TEXT, `icon` TEXT, `color` TEXT, `parentId` INTEGER NOT NULL, `level` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `transactions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` TEXT, `accountId` INTEGER NOT NULL, `categoryId` INTEGER NOT NULL, `amount` REAL NOT NULL, `description` TEXT, `date` INTEGER, `note` TEXT, FOREIGN KEY(`accountId`) REFERENCES `accounts`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_accountId` ON `transactions` (`accountId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_categoryId` ON `transactions` (`categoryId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `budgets` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `categoryId` INTEGER NOT NULL, `categoryName` TEXT, `amount` REAL NOT NULL, `spent` REAL NOT NULL, `month` INTEGER NOT NULL, `year` INTEGER NOT NULL, `type` TEXT, FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_budgets_categoryId` ON `budgets` (`categoryId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '184e518f3e6cab53bc903898e70275a1')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `accounts`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `transactions`");
        db.execSQL("DROP TABLE IF EXISTS `budgets`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsAccounts = new HashMap<String, TableInfo.Column>(5);
        _columnsAccounts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("balance", new TableInfo.Column("balance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAccounts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAccounts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAccounts = new TableInfo("accounts", _columnsAccounts, _foreignKeysAccounts, _indicesAccounts);
        final TableInfo _existingAccounts = TableInfo.read(db, "accounts");
        if (!_infoAccounts.equals(_existingAccounts)) {
          return new RoomOpenHelper.ValidationResult(false, "accounts(com.example.accountapp.data.entity.Account).\n"
                  + " Expected:\n" + _infoAccounts + "\n"
                  + " Found:\n" + _existingAccounts);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(7);
        _columnsCategories.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("icon", new TableInfo.Column("icon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("parentId", new TableInfo.Column("parentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("level", new TableInfo.Column("level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.example.accountapp.data.entity.Category).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsTransactions = new HashMap<String, TableInfo.Column>(8);
        _columnsTransactions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("accountId", new TableInfo.Column("accountId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("date", new TableInfo.Column("date", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTransactions = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysTransactions.add(new TableInfo.ForeignKey("accounts", "CASCADE", "NO ACTION", Arrays.asList("accountId"), Arrays.asList("id")));
        _foreignKeysTransactions.add(new TableInfo.ForeignKey("categories", "CASCADE", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTransactions = new HashSet<TableInfo.Index>(2);
        _indicesTransactions.add(new TableInfo.Index("index_transactions_accountId", false, Arrays.asList("accountId"), Arrays.asList("ASC")));
        _indicesTransactions.add(new TableInfo.Index("index_transactions_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        final TableInfo _infoTransactions = new TableInfo("transactions", _columnsTransactions, _foreignKeysTransactions, _indicesTransactions);
        final TableInfo _existingTransactions = TableInfo.read(db, "transactions");
        if (!_infoTransactions.equals(_existingTransactions)) {
          return new RoomOpenHelper.ValidationResult(false, "transactions(com.example.accountapp.data.entity.TransactionEntity).\n"
                  + " Expected:\n" + _infoTransactions + "\n"
                  + " Found:\n" + _existingTransactions);
        }
        final HashMap<String, TableInfo.Column> _columnsBudgets = new HashMap<String, TableInfo.Column>(8);
        _columnsBudgets.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgets.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgets.put("categoryName", new TableInfo.Column("categoryName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgets.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgets.put("spent", new TableInfo.Column("spent", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgets.put("month", new TableInfo.Column("month", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgets.put("year", new TableInfo.Column("year", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgets.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBudgets = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysBudgets.add(new TableInfo.ForeignKey("categories", "CASCADE", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesBudgets = new HashSet<TableInfo.Index>(1);
        _indicesBudgets.add(new TableInfo.Index("index_budgets_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        final TableInfo _infoBudgets = new TableInfo("budgets", _columnsBudgets, _foreignKeysBudgets, _indicesBudgets);
        final TableInfo _existingBudgets = TableInfo.read(db, "budgets");
        if (!_infoBudgets.equals(_existingBudgets)) {
          return new RoomOpenHelper.ValidationResult(false, "budgets(com.example.accountapp.data.entity.Budget).\n"
                  + " Expected:\n" + _infoBudgets + "\n"
                  + " Found:\n" + _existingBudgets);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "184e518f3e6cab53bc903898e70275a1", "95cf02b0dd1b17cac6332fa598b9a93e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "accounts","categories","transactions","budgets");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `accounts`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `transactions`");
      _db.execSQL("DELETE FROM `budgets`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AccountDao.class, AccountDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TransactionDao.class, TransactionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BudgetDao.class, BudgetDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public AccountDao accountDao() {
    if (_accountDao != null) {
      return _accountDao;
    } else {
      synchronized(this) {
        if(_accountDao == null) {
          _accountDao = new AccountDao_Impl(this);
        }
        return _accountDao;
      }
    }
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }

  @Override
  public TransactionDao transactionDao() {
    if (_transactionDao != null) {
      return _transactionDao;
    } else {
      synchronized(this) {
        if(_transactionDao == null) {
          _transactionDao = new TransactionDao_Impl(this);
        }
        return _transactionDao;
      }
    }
  }

  @Override
  public BudgetDao budgetDao() {
    if (_budgetDao != null) {
      return _budgetDao;
    } else {
      synchronized(this) {
        if(_budgetDao == null) {
          _budgetDao = new BudgetDao_Impl(this);
        }
        return _budgetDao;
      }
    }
  }
}
