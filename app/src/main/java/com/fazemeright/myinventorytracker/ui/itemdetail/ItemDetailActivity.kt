package com.fazemeright.myinventorytracker.ui.itemdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag
import com.fazemeright.myinventorytracker.databinding.ActivityItemDetailBinding
import com.fazemeright.myinventorytracker.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ItemDetailActivity : BaseActivity<ActivityItemDetailBinding>() {

    val viewModel: ItemDetailViewModel by viewModels()

    @Inject
    lateinit var selectedBag: ItemWithBag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.item.observe(this, { item ->
            Timber.d("Detail Item: $item")
            item?.let {
                binding.item = it
            }
        })
        viewModel.navigateBackToItemList.observe(this, { navigate ->
            if (navigate) {
                finish()
                viewModel.onNavigationToItemListFinished()
            }
        })

        viewModel.onItemDeleteComplete.observe(this, { deleted ->
            if (deleted) {
                showToast(getString(R.string.item_deleted_successfully))
                finish()
                viewModel.onItemDeleteFinished()
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
            R.id.action_delete_item -> {
                viewModel.deleteItem()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int = R.layout.activity_item_detail
}