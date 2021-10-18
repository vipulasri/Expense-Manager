package com.vipulasri.expensemanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import com.vipulasri.expensemanager.data.local.entity.TransactionType
import com.vipulasri.expensemanager.model.TransactionUiModel

/**
 * Created by Vipul Asri on 15/10/21.
 */

@Dao
interface TransactionDao {

    @Query("SELECT SUM(amount) FROM `transaction` WHERE type=${TransactionType.INCOME}")
    fun getTotalIncome(): LiveData<Double>

    @Query("SELECT SUM(amount) FROM `transaction` WHERE type=${TransactionType.EXPENSE}")
    fun getTotalExpense(): LiveData<Double>

    @Query("SELECT * FROM `transaction` ORDER BY timestamp DESC")
    fun getAllTransactionsLiveData(): LiveData<List<TransactionEntity>>

    @Query(
        "SELECT date,  " +
                "GROUP_CONCAT('[{id:\"' || id || '\", type:\"' || type || '\", description:\"' || description || '\", amount:\"' || amount || '\", date:\"' || date || '\", timestamp:\"' || timestamp ||'\"}]') transactions " +
                "FROM `transaction` GROUP BY date ORDER BY timestamp DESC"
    )
    fun getAllTransactionUiModelLiveData(): LiveData<List<TransactionUiModel>>

    @Insert
    suspend fun insert(transaction: TransactionEntity): Long

    @Query("DELETE FROM `transaction` WHERE id = :id")
    suspend fun delete(id: Int)

}