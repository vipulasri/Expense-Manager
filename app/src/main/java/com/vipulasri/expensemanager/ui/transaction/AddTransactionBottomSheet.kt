package com.vipulasri.expensemanager.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vipulasri.expensemanager.R
import com.vipulasri.expensemanager.data.local.entity.TransactionType
import com.vipulasri.expensemanager.databinding.BottomsheetAddTransactionBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Vipul Asri on 17/10/21.
 */

@AndroidEntryPoint
class AddTransactionBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val dialog = AddTransactionBottomSheet()
            dialog.show(fragmentManager, "AddTransactionBottomSheet")
        }
    }

    private lateinit var binding: BottomsheetAddTransactionBinding
    private val viewModel: AddTransactionVM by viewModels()
    private var selectedType: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetAddTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupTransactionType()
        binding.buttonAdd.setOnClickListener { onAddClick() }
    }

    private fun setupTransactionType() {
        val items = resources.getStringArray(R.array.transaction_type)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        (binding.dropdownTransactionType.editText as? AutoCompleteTextView)?.run {
            setAdapter(adapter)
            onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, position, p3 ->
                    selectedType = when (items[position]) {
                        "Income" -> TransactionType.INCOME
                        else -> TransactionType.EXPENSE
                    }
                }
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            when (viewState) {
                is AddTransactionViewState.TransactionTypeError -> {
                    binding.dropdownTransactionType.error = getString(viewState.message)
                }
                is AddTransactionViewState.DescriptionError -> {
                    binding.inputTransactionDescription.error = getString(viewState.message)
                }
                is AddTransactionViewState.AmountError -> {
                    binding.inputTransactionAmount.error = getString(viewState.message)
                }
                is AddTransactionViewState.Success -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.transaction_successful,
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                }
            }
        })
    }

    private fun onAddClick() {
        clearTextFieldErrors()

        val description = binding.editTransactionDescription.text.toString().trim()
        val amount = binding.editTransactionAmount.text.toString().trim()
        viewModel.addTransaction(selectedType, description, amount)
    }

    private fun clearTextFieldErrors() {
        // clear errors
        binding.run {
            dropdownTransactionType.error = null
            inputTransactionDescription.error = null
            inputTransactionAmount.error = null
        }
    }

}