package com.vipulasri.expensemanager.data

import androidx.lifecycle.LiveData
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

    fun getTotalIncome(): LiveData<Double> {
        return transactionDao.getTotalIncome()
    }

    fun getTotalExpense(): LiveData<Double> {
        return transactionDao.getTotalExpense()
    }

    suspend fun addIncome(amount: Double, description: String? = null): Long {
        return withContext(dispatcher) {
            transactionDao.insert(
                TransactionEntity(
                    type = 1,
                    amount = amount,
                    description = description,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun addExpense(amount: Double, description: String? = null): Long {
        return withContext(dispatcher) {
            transactionDao.insert(
                TransactionEntity(
                    type = 2,
                    amount = amount,
                    description = description,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun getAllTransactions(): LiveData<List<TransactionEntity>> {
        return transactionDao.getAllTransactions()
    }

}