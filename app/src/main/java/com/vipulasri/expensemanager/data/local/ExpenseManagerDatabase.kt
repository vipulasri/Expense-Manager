package com.vipulasri.expensemanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vipulasri.expensemanager.data.local.dao.TransactionDao
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity

/**
 * Created by Vipul Asri on 15/10/21.
 */

@Database(
    version = 1,
    entities = [TransactionEntity::class],
    exportSchema = false
)
abstract class ExpenseManagerDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {
        const val DB_NAME = "expense_manager_db"
    }
}