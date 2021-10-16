package com.vipulasri.expensemanager.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import com.vipulasri.expensemanager.databinding.ItemTransactionBinding

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
        holder.bindData(getItem(position))
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(entity: TransactionEntity) {
            binding.textDescription.text = entity.description ?: "--"

            val amountString = StringBuilder().apply {
                if (entity.type == 2) {
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