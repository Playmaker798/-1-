package com.example.accountapp.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.accountapp.data.dao.AccountDao;
import com.example.accountapp.data.dao.BudgetDao;
import com.example.accountapp.data.dao.CategoryDao;
import com.example.accountapp.data.dao.TransactionDao;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.entity.Budget;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.entity.TransactionEntity;

@Database(entities = {
        Account.class,
        Category.class,
        TransactionEntity.class,
        Budget.class
    },
    version = 3,
    exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "account_db";
    private static volatile AppDatabase instance;

    public abstract AccountDao accountDao();
    public abstract CategoryDao categoryDao();
    public abstract TransactionDao transactionDao();
    public abstract BudgetDao budgetDao();

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // 创建临时表
            database.execSQL("CREATE TABLE IF NOT EXISTS categories_new (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "icon TEXT NOT NULL, " +
                    "parentId INTEGER NOT NULL DEFAULT 0, " +
                    "level INTEGER NOT NULL DEFAULT 1)");

            // 将旧数据复制到新表
            database.execSQL("INSERT INTO categories_new (id, name, type, icon, parentId, level) " +
                    "SELECT id, name, type, icon, 0, 1 FROM categories");

            // 删除旧表
            database.execSQL("DROP TABLE categories");

            // 重命名新表
            database.execSQL("ALTER TABLE categories_new RENAME TO categories");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Drop and recreate the categories table with all required columns
            database.execSQL("DROP TABLE IF EXISTS categories");
            database.execSQL("CREATE TABLE IF NOT EXISTS categories (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "icon TEXT NOT NULL, " +
                    "color TEXT NOT NULL, " +
                    "parentId INTEGER NOT NULL DEFAULT 0, " +
                    "level INTEGER NOT NULL DEFAULT 1)");
        }
    };

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
} 