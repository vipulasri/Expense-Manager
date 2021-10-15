package com.vipulasri.expensemanager.di

import android.content.Context
import androidx.room.Room
import com.vipulasri.expensemanager.data.local.ExpenseManagerDatabase
import com.vipulasri.expensemanager.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Vipul Asri on 15/10/21.
 */

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ExpenseManagerDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseManagerDatabase::class.java,
            ExpenseManagerDatabase.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideSessionDao(database: ExpenseManagerDatabase): TransactionDao {
        return database.transactionDao()
    }

}