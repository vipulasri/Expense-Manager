package com.vipulasri.expensemanager.ui.home

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipulasri.expensemanager.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeVM by viewModels()

    private val adapter = TransactionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.content.recyclerView.run {
            layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
            adapter = (this@HomeActivity).adapter
        }
    }

    private fun setupObservers() {
        viewModel.totalIncome.observe(this, { income ->
            updateAmount(binding.content.textIncome, income)
        })

        viewModel.totalExpense.observe(this, { expenses ->
            updateAmount(binding.content.textExpense, expenses)
        })

        viewModel.balance.observe(this, { balance ->
            updateAmount(binding.content.textBalance, balance)
        })

        viewModel.transactions.observe(this, { transactions ->
            adapter.submitList(transactions.orEmpty().toMutableList())
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

}