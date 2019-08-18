package com.fazemeright.myinventorytracker.additem

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var selectedBagName: String? = null
    lateinit var viewModelFactory: AddItemViewModelFactory
    lateinit var viewModel: AddItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityAddItemBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)

        val application = requireNotNull(this).application

        val dataSource = InventoryDatabase.getInstance(application)

        viewModelFactory = AddItemViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddItemViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.bagNames.observe(this, Observer { bagNames ->
            Log.d("##DebugData", bagNames.size.toString())

            // Create an ArrayAdapter using a simple spinner layout and languages array
            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, bagNames)
            // Set layout to use when the list of choices appear
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            binding.spinnerBag.adapter = aa

            binding.spinnerBag.onItemSelectedListener = this
        })

        viewModel.navigateBackToItemList.observe(this, Observer { navigate ->
            if (navigate) {
                finish()
                viewModel.onNavigationToAddItemFinished()
            }
        })

        binding.btnAddItem.setOnClickListener {
            selectedBagName?.let { bagName ->
                viewModel.onAddClicked(
                    binding.edtItemName.text.toString(),
                    bagName,
                    binding.edtItemDesc.text.toString(),
                    binding.edtItemQuantity.text.toString()
                )
            }
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        selectedBagName = viewModel.bagNames.value?.get(position)
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
