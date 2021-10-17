package com.vipulasri.expensemanager.ui.transaction

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vipulasri.expensemanager.R
import com.vipulasri.expensemanager.data.TransactionRepository
import com.vipulasri.expensemanager.data.local.entity.TransactionType
import com.vipulasri.expensemanager.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Vipul Asri on 17/10/21.
 */

@HiltViewModel
class AddTransactionVM @Inject constructor(
    private val repository: TransactionRepository
) : BaseVM() {

    val viewState: LiveData<AddTransactionViewState> = MutableLiveData()

    fun addTransaction(type: Int?, description: String, amount: String) {

        if (validate(type, description, amount).not()) return

        viewModelScope.launch {
            repository.addTransaction(
                type ?: TransactionType.INCOME,
                amount.toDouble(),
                description,
            )

            viewState.setValue(AddTransactionViewState.Success)
        }
    }

    private fun validate(type: Int?, description: String, amount: String): Boolean {
        return when {
            type == null -> {
                viewState.setValue(
                    AddTransactionViewState.TransactionTypeError(
                        R.string.error_message_transaction_type
                    )
                )
                false
            }
            description.isEmpty() -> {
                viewState.setValue(
                    AddTransactionViewState.DescriptionError(
                        R.string.error_message_transaction_description
                    )
                )
                false
            }
            amount.isEmpty() || amount.toDouble() == 0.0 -> {
                viewState.setValue(
                    AddTransactionViewState.AmountError(
                        R.string.error_message_transaction_amount
                    )
                )
                false
            }
            else -> true
        }
    }

}

sealed class AddTransactionViewState {
    class TransactionTypeError(@StringRes val message: Int) : AddTransactionViewState()
    class DescriptionError(@StringRes val message: Int) : AddTransactionViewState()
    class AmountError(@StringRes val message: Int) : AddTransactionViewState()
    object Success : AddTransactionViewState()
}