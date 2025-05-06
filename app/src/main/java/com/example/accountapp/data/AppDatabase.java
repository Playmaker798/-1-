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
    version = 5,
    exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "account_db";
    private static volatile AppDatabase instance;

    public abstract AccountDao accountDao();
    public abstract CategoryDao categoryDao();
    public abstract TransactionDao transactionDao();
    public abstract BudgetDao budgetDao();

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Drop all tables and recreate them
            database.execSQL("DROP TABLE IF EXISTS categories");
            database.execSQL("DROP TABLE IF EXISTS transactions");
            database.execSQL("DROP TABLE IF EXISTS accounts");
            database.execSQL("DROP TABLE IF EXISTS budgets");

            // Recreate categories table
            database.execSQL("CREATE TABLE IF NOT EXISTS categories (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "icon TEXT NOT NULL, " +
                    "color TEXT NOT NULL, " +
                    "parentId INTEGER NOT NULL DEFAULT 0, " +
                    "level INTEGER NOT NULL DEFAULT 1)");

            // Recreate other tables
            database.execSQL("CREATE TABLE IF NOT EXISTS accounts (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "balance REAL NOT NULL DEFAULT 0, " +
                    "note TEXT)");

            database.execSQL("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "accountId INTEGER NOT NULL, " +
                    "categoryId INTEGER NOT NULL, " +
                    "amount REAL NOT NULL, " +
                    "description TEXT, " +
                    "note TEXT, " +
                    "date INTEGER NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "FOREIGN KEY(accountId) REFERENCES accounts(id), " +
                    "FOREIGN KEY(categoryId) REFERENCES categories(id))");

            database.execSQL("CREATE TABLE IF NOT EXISTS budgets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "categoryId INTEGER NOT NULL, " +
                    "amount REAL NOT NULL, " +
                    "month INTEGER NOT NULL, " +
                    "year INTEGER NOT NULL, " +
                    "FOREIGN KEY(categoryId) REFERENCES categories(id))");
        }
    };

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME)
                    .addMigrations(MIGRATION_4_5)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
} 