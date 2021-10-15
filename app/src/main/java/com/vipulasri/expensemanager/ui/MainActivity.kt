package com.vipulasri.expensemanager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vipulasri.expensemanager.data.TransactionRepository
import com.vipulasri.expensemanager.data.getErrorOrNull
import com.vipulasri.expensemanager.data.getSuccessOrNull
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity
import com.vipulasri.expensemanager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: TransactionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.content.buttonIncome.setOnClickListener {
            addIncome()
        }

        binding.content.buttonExpense.setOnClickListener {
            addExpense()
        }

        lifecycleScope.launch {
            repository.getTotalIncome().collect {
                binding.content.textIncome.text = it.toString()
            }

            repository.getTotalExpense().collect {
                binding.content.textExpense.text = it.toString()
            }
        }

        binding.fab.setOnClickListener { view ->

        }
    }

    private fun addIncome() {
        lifecycleScope.launch {
            repository.saveTransaction(
                TransactionEntity(
                    type = 1,
                    amount = 200.0,
                    description = "Income"
                )
            )
        }
    }

    private fun addExpense() {
        lifecycleScope.launch {
            repository.saveTransaction(
                TransactionEntity(
                    type = 2,
                    amount = 100.0,
                    description = "Income"
                )
            )
        }
    }

}