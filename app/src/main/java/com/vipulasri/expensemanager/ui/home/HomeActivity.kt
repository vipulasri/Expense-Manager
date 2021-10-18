package com.vipulasri.expensemanager.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyTouchHelper
import com.vipulasri.expensemanager.TransactionBindingModel_
import com.vipulasri.expensemanager.databinding.ActivityHomeBinding
import com.vipulasri.expensemanager.ui.transaction.AddTransactionBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeVM by viewModels()

    private val controller = TransactionController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fabAddTransaction.setOnClickListener {
            AddTransactionBottomSheet.show(supportFragmentManager)
        }

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.content.recyclerView.run {
            layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
            adapter = controller.adapter
            addSwipeToDelete(this)
        }
    }

    private fun setupObservers() {
        viewModel.totalIncome.observe(this, { income ->
            updateAmount(binding.content.textIncome, income)
            updateExpenseProgress()
        })

        viewModel.totalExpense.observe(this, { expenses ->
            updateAmount(binding.content.textExpense, expenses)
            updateExpenseProgress()
        })

        viewModel.balance.observe(this, { balance ->
            updateAmount(binding.content.textBalance, balance)
        })

        viewModel.transactionUiModels.observe(this, { transactions ->
            Log.d("HomeActivity", "transaction ui models: $transactions")
            controller.setData(transactions)
        })
    }

    private fun updateAmount(textView: TextView, amount: Double?) {
        textView.text = amount?.let { safeAmount ->
            StringBuilder().apply {
                if (safeAmount < 0) {
                    append("- ")
                }
                append("$${abs(safeAmount)}")
            }
        } ?: "--"
    }

    private fun updateExpenseProgress() {
        val income = viewModel.totalIncome.value?.toInt() ?: 0
        val expenses = viewModel.totalExpense.value?.toInt() ?: 0

        if (income == 0 && expenses == 0) {
            binding.content.progressExpense.isVisible = false
            return
        }

        binding.content.progressExpense.run {
            isVisible = true
            progress = expenses
            max = income
        }
    }

    private fun addSwipeToDelete(recyclerView: RecyclerView) {
        EpoxyTouchHelper.initSwiping(recyclerView)
            .leftAndRight()
            .withTarget(TransactionBindingModel_::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<TransactionBindingModel_>() {
                override fun onSwipeCompleted(
                    model: TransactionBindingModel_?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    model?.transaction()?.let { transaction ->
                        viewModel.deleteTransaction(transaction)
                    }
                }
            })
    }

}