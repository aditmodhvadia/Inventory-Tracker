package com.fazemeright.myinventorytracker.ui.additem

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.FragmentAddItemBinding
import com.fazemeright.myinventorytracker.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddItemFragment : BaseFragment<FragmentAddItemBinding>(), AdapterView.OnItemSelectedListener {

    private var selectedBagName: String? = null

    val viewModel: AddItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.navigateBackToItemList.observe(
            viewLifecycleOwner,
            { navigate ->
                if (navigate) {
                    findNavController().navigate(AddItemFragmentDirections.actionAddItemFragmentToItemListFragment())
                    viewModel.onNavigationToAddItemFinished()
                }
            }
        )

        viewModel.bagNames.observe(viewLifecycleOwner) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.tvBagname.adapter = adapter
            binding.tvBagname.onItemSelectedListener = this
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_item -> addBag()
            android.R.id.home -> findNavController().navigate(AddItemFragmentDirections.actionAddItemFragmentToItemListFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addBag() {
        selectedBagName?.let { bagName ->
            viewModel.onAddClicked(
                binding.edtItemName.text.toString(),
                bagName,
                binding.edtItemDesc.text.toString(),
                binding.edtItemQuantity.text.toString()
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_add_item, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        selectedBagName = viewModel.bagNames.value?.get(position)
    }

    override fun getViewBinding(): FragmentAddItemBinding =
        FragmentAddItemBinding.inflate(layoutInflater)
}