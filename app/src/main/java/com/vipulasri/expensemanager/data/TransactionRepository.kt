package com.vipulasri.expensemanager.data

import com.vipulasri.expensemanager.data.local.dao.TransactionDao
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Vipul Asri on 15/10/21.
 */

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getTotalIncome(): SafeResult<Double> {
        return withContext(dispatcher) {
            SafeResult.Success(transactionDao.getTotalIncome())
        }
    }

    suspend fun getTotalExpense(): SafeResult<Double> {
        return withContext(dispatcher) {
            SafeResult.Success(transactionDao.getTotalExpense())
        }
    }

    suspend fun saveTransaction(transactionEntity: TransactionEntity): Long {
        return withContext(dispatcher) {
            transactionDao.insert(transactionEntity)
        }
    }

}