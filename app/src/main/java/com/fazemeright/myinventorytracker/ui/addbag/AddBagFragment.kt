package com.fazemeright.myinventorytracker.ui.addbag

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazemeright.myinventorytracker.databinding.FragmentAddBagBinding
import com.fazemeright.myinventorytracker.utils.showToast
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddBagFragment @Inject constructor() : Fragment(), ColorPickerDialogListener {

    private var bagColor: Int = 0
    private val dialogId: Int = 1001

    private val viewModel: AddBagViewModel by viewModels()
    private lateinit var binding: FragmentAddBagBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAddBagBinding.inflate(inflater).apply {
            lifecycleOwner = this@AddBagFragment
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        /*(requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }*/
        binding.btnChooseColor.setOnClickListener {
            ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .setAllowPresets(false)
                .setDialogId(dialogId)
                .setColor(Color.BLACK)
                .show(requireActivity())
        }

        binding.btnAddBag.setOnClickListener {
            viewModel.onAddBagClicked(
                binding.edtBagName.text.toString(),
                bagColor,
                binding.edtBagDesc.text.toString()
            )
        }

        viewModel.navigateBackToItemList.observe(
            viewLifecycleOwner
        ) { navigate ->
            if (navigate) {
                findNavController().navigate(AddBagFragmentDirections.actionAddBagFragmentToItemListFragment())
                viewModel.onNavigationToAddItemFinished()
            }
        }
    }

    override fun onDialogDismissed(dialogId: Int) {
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        showToast("Selected Color: #" + Integer.toHexString(color))
        binding.viewBagColorDisplay.setBackgroundColor(color)
        bagColor = color
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigate(AddBagFragmentDirections.actionAddBagFragmentToItemListFragment())
        }
        return true
    }
}
