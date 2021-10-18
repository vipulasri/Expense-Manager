package com.vipulasri.expensemanager.model

import androidx.room.Entity
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity

/**
 * Created by Vipul Asri on 18/10/21.
 */

data class TransactionUiModel(
    val date: String?,
    val transactions: String?
)