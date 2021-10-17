package com.vipulasri.expensemanager.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import com.vipulasri.expensemanager.data.local.entity.TransactionType
import com.vipulasri.expensemanager.databinding.ItemTransactionBinding
import com.vipulasri.expensemanager.extensions.toDate

/**
 * Created by Vipul Asri on 16/10/21.
 */

class TransactionAdapter :
    ListAdapter<TransactionEntity, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentItem = getItem(position)
        val currentItemDate = currentItem.timestamp.toDate()
        val hasSection =
            position == 0 || (currentItemDate != getItem(position.minus(1)).timestamp.toDate())

        holder.bindData(currentItem, hasSection, currentItemDate)
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(entity: TransactionEntity, hasSection: Boolean = false, sectionTitle: String) {

            if (hasSection) {
                binding.textDate.text = sectionTitle
                binding.textDate.isVisible = true
            } else {
                binding.textDate.isVisible = false
            }

            binding.textDescription.text = entity.description ?: "--"

            val amountString = StringBuilder().apply {
                if (entity.type == TransactionType.EXPENSE) {
                    append("- ")
                }
                append("$${entity.amount}")
            }

            binding.textAmount.text = amountString
        }
    }
}

class TransactionDiffCallback : DiffUtil.ItemCallback<TransactionEntity>() {
    override fun areItemsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: TransactionEntity,
        newItem: TransactionEntity
    ): Boolean = oldItem == newItem
}