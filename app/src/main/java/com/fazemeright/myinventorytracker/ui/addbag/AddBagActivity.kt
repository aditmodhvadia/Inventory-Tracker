package com.fazemeright.myinventorytracker.ui.addbag

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.ActivityAddBagBinding
import com.fazemeright.myinventorytracker.ui.base.BaseActivity
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.collapsing_toolbar.*

@AndroidEntryPoint
class AddBagActivity : BaseActivity<ActivityAddBagBinding>(), ColorPickerDialogListener {

    private var bagColor: Int = 0
    private val dialogId: Int = 1001

    private val viewModel: AddBagViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
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
            viewModel.onAddBagClicked(
                binding.edtBagName.text.toString(),
                bagColor,
                binding.edtBagDesc.text.toString()
            )
        }

        viewModel.navigateBackToItemList.observe(this, { navigate ->
            if (navigate) {
                finish()
                viewModel.onNavigationToAddItemFinished()
            }
        })

    }

    override fun onDialogDismissed(dialogId: Int) {
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        Toast.makeText(this, "Selected Color: #" + Integer.toHexString(color), Toast.LENGTH_SHORT)
            .show()
        binding.viewBagColorDisplay.setBackgroundColor(color)
        bagColor = color
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun getLayoutId(): Int = R.layout.activity_add_bag
}
