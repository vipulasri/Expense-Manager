package com.vipulasri.expensemanager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity

/**
 * Created by Vipul Asri on 15/10/21.
 */

@Dao
interface TransactionDao {

    @Query("SELECT SUM(amount) FROM `transaction` WHERE type=1")
    suspend fun getTotalIncome(): Double

    @Query("SELECT SUM(amount) FROM `transaction` WHERE type=2")
    suspend fun getTotalExpense(): Double

    @Insert
    suspend fun insert(transaction: TransactionEntity): Long

}