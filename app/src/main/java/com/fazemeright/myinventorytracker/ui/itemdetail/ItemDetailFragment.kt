package com.fazemeright.myinventorytracker.ui.itemdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.FragmentItemDetailBinding
import com.fazemeright.myinventorytracker.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    val viewModel: ItemDetailViewModel by viewModels()
    private lateinit var binding: FragmentItemDetailBinding
    private val navArgs by navArgs<ItemDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentItemDetailBinding.inflate(inflater).apply {
            lifecycleOwner = this@ItemDetailFragment
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

        viewModel.setNavArguments(navArgs)

        viewModel.item.observe(
            viewLifecycleOwner
        ) { item ->
            Timber.d("Detail Item: $item")
            item?.let {
                binding.item = it
            }
        }
        viewModel.navigateBackToItemList.observe(
            viewLifecycleOwner
        ) { navigate ->
            if (navigate) {
                findNavController().navigate(ItemDetailFragmentDirections.actionItemDetailFragmentToItemListFragment())
                viewModel.onNavigationToItemListFinished()
            }
        }

        viewModel.onItemDeleteComplete.observe(
            viewLifecycleOwner
        ) { deleted ->
            if (deleted) {
                showToast(getString(R.string.item_deleted_successfully))
                findNavController().navigate(ItemDetailFragmentDirections.actionItemDetailFragmentToItemListFragment())
                viewModel.onItemDeleteFinished()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_update_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(ItemDetailFragmentDirections.actionItemDetailFragmentToItemListFragment())
                return true
            }
            R.id.action_delete_item -> {
                viewModel.deleteItem()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
