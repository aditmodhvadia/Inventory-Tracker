package com.fazemeright.myinventorytracker.bag

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.databinding.ActivityAddBagBinding
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class AddBagActivity : AppCompatActivity(), ColorPickerDialogListener {

    private var bagColor: Int = 0
    private val dialogId: Int = 1001
    lateinit var viewModelFactory: AddBagViewModelFactory
    lateinit var viewModel: AddBagViewModel
    lateinit var binding: ActivityAddBagBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_bag)

        val application = requireNotNull(this).application

        val dataSource = InventoryDatabase.getInstance(application)

        viewModelFactory = AddBagViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddBagViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Add Bag"
        }

        binding.btnChooseColor.setOnClickListener {
            ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .setAllowPresets(false)
                .setDialogId(dialogId)
                .setColor(Color.BLACK)
                .show(this)
        }

        binding.btnAddBag.setOnClickListener {
            viewModel.onAddBagClicked(binding.edtBagName.text.toString(), bagColor, binding.edtBagDesc.text.toString())
        }

        viewModel.navigateBackToItemList.observe(this, Observer { navigate ->
            if (navigate) {
                finish()
                viewModel.onNavigationToAddItemFinished()
            }
        })

    }

    override fun onDialogDismissed(dialogId: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        Toast.makeText(this, "Selected Color: #" + Integer.toHexString(color), Toast.LENGTH_SHORT).show()
        binding.viewBagColorDisplay.setBackgroundColor(color)
        bagColor = color
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}
