package com.example.accountapp.di

import android.content.Context
import androidx.room.Room
import com.example.accountapp.data.AppDatabase
import com.example.accountapp.data.dao.AccountDao
import com.example.accountapp.data.dao.CategoryDao
import com.example.accountapp.data.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "account_app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAccountDao(database: AppDatabase): AccountDao {
        return database.accountDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }
} 