package com.vipulasri.expensemanager.ui.home

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vipulasri.expensemanager.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupObservers()
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
    }

    private fun updateAmount(textView: TextView, amount: Double?) {
        textView.text = (amount?: "--") as? CharSequence
    }

}