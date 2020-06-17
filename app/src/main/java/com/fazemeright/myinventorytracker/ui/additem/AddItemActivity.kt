package com.fazemeright.myinventorytracker.ui.additem

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.databinding.ActivityAddItemBinding
import com.fazemeright.myinventorytracker.ui.itemlist.BaseViewModelFactory
import kotlinx.android.synthetic.main.activity_add_item.*

class AddItemActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var selectedBagName: String? = null

    val viewModel: AddItemViewModel by viewModels {
        BaseViewModelFactory(
            application = application,
            dataSource = InventoryDatabase.getInstance(application)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityAddItemBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_item)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.add_item_title)
        }

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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_item -> addBag()
            android.R.id.home -> finish()
        }
        return true
    }

    private fun addBag() {
        selectedBagName?.let { bagName ->
            viewModel.onAddClicked(
                edtItemName.text.toString(),
                bagName,
                edtItemDesc.text.toString(),
                edtItemQuantity.text.toString()
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_item, menu)
        return true
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        selectedBagName = viewModel.bagNames.value?.get(position)
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
