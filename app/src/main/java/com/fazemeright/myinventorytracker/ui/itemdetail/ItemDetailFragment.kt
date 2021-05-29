package com.fazemeright.myinventorytracker.ui.itemdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.FragmentItemDetailBinding
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag
import com.fazemeright.myinventorytracker.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ItemDetailFragment : BaseFragment<FragmentItemDetailBinding>() {

    val viewModel: ItemDetailViewModel by viewModels()

    @Inject
    lateinit var selectedBag: ItemWithBag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

//        setSupportActionBar(toolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.item.observe(
            this,
            { item ->
                Timber.d("Detail Item: $item")
                item?.let {
                    binding.item = it
                }
            }
        )
        viewModel.navigateBackToItemList.observe(
            this,
            { navigate ->
                if (navigate) {
//                    TODO: finish()
                    viewModel.onNavigationToItemListFinished()
                }
            }
        )

        viewModel.onItemDeleteComplete.observe(
            this,
            { deleted ->
                if (deleted) {
                    showToast(getString(R.string.item_deleted_successfully))
//                    TODO: finish()
                    viewModel.onItemDeleteFinished()
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_update_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
//                TODO: finish()
                return true
            }
            R.id.action_delete_item -> {
                viewModel.deleteItem()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getViewBinding(): FragmentItemDetailBinding {
        return FragmentItemDetailBinding.inflate(layoutInflater)
    }
}
