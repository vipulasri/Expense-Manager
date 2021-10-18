package com.vipulasri.expensemanager.ui.home

import com.airbnb.epoxy.TypedEpoxyController
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import com.vipulasri.expensemanager.data.local.entity.TransactionType
import com.vipulasri.expensemanager.model.TransactionUiModel
import com.vipulasri.expensemanager.section
import com.vipulasri.expensemanager.transaction

/**
 * Created by Vipul Asri on 19/10/21.
 */

class TransactionController : TypedEpoxyController<List<TransactionUiModel>>() {

    override fun buildModels(data: List<TransactionUiModel>?) {
        data?.takeIf { it.isNotEmpty() }
            ?.forEach { uiModel ->
                if (uiModel.transactions.isEmpty()) return@forEach
                buildSection(uiModel.date)
                uiModel.transactions.forEach { transaction ->
                    buildTransaction(transaction)
                }
            }
    }

    private fun buildSection(title: String) {
        section {
            id(title)
            date(title)
        }
    }

    private fun buildTransaction(entity: TransactionEntity) {

        val amountString = StringBuilder().apply {
            if (entity.type == TransactionType.EXPENSE) {
                append("- ")
            }
            append("$${entity.amount}")
        }

        transaction {
            id(entity.toString())
            transaction(entity)
            amount(amountString.toString())
        }
    }

}