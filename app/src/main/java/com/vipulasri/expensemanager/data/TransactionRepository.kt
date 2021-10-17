package com.vipulasri.expensemanager.data

import androidx.lifecycle.LiveData
import com.vipulasri.expensemanager.data.local.dao.TransactionDao
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import com.vipulasri.expensemanager.data.local.entity.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
                    type = TransactionType.INCOME,
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
                    type = TransactionType.EXPENSE,
                    amount = amount,
                    description = description,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun getAllTransactionsLiveData(): LiveData<List<TransactionEntity>> {
        return transactionDao.getAllTransactionsLiveData()
    }

}