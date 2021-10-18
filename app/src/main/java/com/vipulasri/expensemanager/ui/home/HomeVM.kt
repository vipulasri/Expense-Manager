package com.vipulasri.expensemanager.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.vipulasri.expensemanager.data.TransactionRepository
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import com.vipulasri.expensemanager.model.TransactionUiModel
import com.vipulasri.expensemanager.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Vipul Asri on 16/10/21.
 */

@HiltViewModel
class HomeVM @Inject constructor(
    private val repository: TransactionRepository
) : BaseVM() {

    val totalIncome: LiveData<Double> = repository.getTotalIncome()
    val totalExpense: LiveData<Double> = repository.getTotalExpense()

    val balance: LiveData<Double> = MediatorLiveData<Double>()
        .apply {

            fun update() {
                val income = totalIncome.value ?: kotlin.run {
                    value = null
                    return
                }

                val expense = totalExpense.value ?: kotlin.run {
                    value = null
                    return
                }

                value = income - expense
            }

            addSource(totalIncome) { update() }
            addSource(totalExpense) { update() }

            update()
        }

    val transactionUiModels: LiveData<List<TransactionUiModel>> =
        repository.getTransactionUiModelLiveData()

    fun deleteTransaction(entity: TransactionEntity) {
        viewModelScope.launch {
            repository.deleteTransaction(entity)
        }
    }

}