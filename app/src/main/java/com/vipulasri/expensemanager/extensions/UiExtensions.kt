package com.vipulasri.expensemanager.extensions

import com.vipulasri.expensemanager.R
import com.vipulasri.expensemanager.data.local.entity.TransactionType

/**
 * Created by Vipul Asri on 19/10/21.
 */

fun Int.getColorByTransactionType(): Int {
    return when (this) {
        TransactionType.INCOME -> {
            R.color.income
        }
        TransactionType.EXPENSE -> {
            R.color.expense
        }
        else -> android.R.color.black
    }
}