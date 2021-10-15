package com.vipulasri.expensemanager.di

import com.vipulasri.expensemanager.data.TransactionRepository
import com.vipulasri.expensemanager.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Vipul Asri on 15/10/21.
 */

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTransactionRepository(
        transactionDao: TransactionDao,
    ): TransactionRepository {
        return TransactionRepository(transactionDao, Dispatchers.IO)
    }

}