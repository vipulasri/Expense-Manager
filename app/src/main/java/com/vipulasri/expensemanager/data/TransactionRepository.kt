package com.vipulasri.expensemanager.data

import androidx.lifecycle.LiveData
import com.vipulasri.expensemanager.data.local.dao.TransactionDao
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import com.vipulasri.expensemanager.data.local.entity.TransactionType
import com.vipulasri.expensemanager.extensions.toDate
import com.vipulasri.expensemanager.model.TransactionUiModel
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
        return addTransaction(TransactionType.INCOME, amount, description)
    }

    suspend fun addExpense(amount: Double, description: String? = null): Long {
        return addTransaction(TransactionType.EXPENSE, amount, description)
    }

    suspend fun addTransaction(type: Int, amount: Double, description: String? = null): Long {
        return withContext(dispatcher) {
            val nowMillis = System.currentTimeMillis()
            transactionDao.insert(
                TransactionEntity(
                    type = type,
                    amount = amount,
                    description = description,
                    date = nowMillis.toDate(),
                    timestamp = nowMillis,
                )
            )
        }
    }

    fun getAllTransactionsLiveData(): LiveData<List<TransactionEntity>> {
        return transactionDao.getAllTransactionsLiveData()
    }

    fun getTransactionUiModelLiveData(): LiveData<List<TransactionUiModel>> {
        return transactionDao.getAllTransactionUiModelLiveData()
    }

    suspend fun deleteTransaction(entity: TransactionEntity) {
        withContext(dispatcher) {
            transactionDao.delete(entity.id)
        }
    }

}