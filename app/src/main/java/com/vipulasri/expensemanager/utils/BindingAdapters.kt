package com.vipulasri.expensemanager.utils

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import com.vipulasri.expensemanager.extensions.getColorByTransactionType

/**
 * Created by Vipul Asri on 19/10/21.
 */

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("applyTextColorBasedOnTransactionType")
    fun setTextColorBasedOnTransactionType(
        textView: TextView,
        entity: TransactionEntity
    ) {
        textView.setTextColor(
            ContextCompat.getColor(
                textView.context,
                entity.type.getColorByTransactionType()
            )
        )
    }

}