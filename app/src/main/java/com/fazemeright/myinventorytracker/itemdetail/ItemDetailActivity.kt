package com.fazemeright.myinventorytracker.itemdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.databinding.ActivityItemDetailBinding

class ItemDetailActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var selectedBagName: String? = null
    lateinit var viewModel: ItemDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityItemDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_item_detail)

        val application = requireNotNull(this).application

        val dataSource = InventoryDatabase.getInstance(application)

        val viewModelFactory =
            ItemDetailViewModelFactory(dataSource, application, intent.getLongExtra("itemId", 0))

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ItemDetailViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle(getString(R.string.item))
        }

        viewModel.bagNames.observe(this, Observer { bagNames ->
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_update_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        selectedBagName = viewModel.bagNames.value?.get(position)
        viewModel.updateBagDesc(selectedBagName)
    }
}