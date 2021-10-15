package com.vipulasri.expensemanager.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Vipul Asri on 15/10/21.
 */

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)