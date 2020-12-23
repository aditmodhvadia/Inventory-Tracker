package com.fazemeright.myinventorytracker.ui.additem

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.ActivityAddItemBinding
import com.fazemeright.myinventorytracker.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*

@AndroidEntryPoint
class AddItemActivity : BaseActivity<ActivityAddItemBinding>(), AdapterView.OnItemSelectedListener {

    private var selectedBagName: String? = null

    val viewModel: AddItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

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

    override fun getLayoutId(): Int = R.layout.activity_add_item
}
