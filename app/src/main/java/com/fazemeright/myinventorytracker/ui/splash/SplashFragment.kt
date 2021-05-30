package com.fazemeright.myinventorytracker.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazemeright.myinventorytracker.databinding.FragmentSplashBinding
import com.fazemeright.myinventorytracker.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideToolBar()
        binding.viewModel = viewModel
        viewModel.isUserSignedIn.observe(requireActivity(), { userIsSignedIn ->
            val action = if (userIsSignedIn) {
                viewModel.syncLocalAndCloudData()
                SplashFragmentDirections.actionSplashFragmentToItemListFragment()
            } else {
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            }
            findNavController().navigate(action)
        })
    }

    override fun getViewBinding(): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        hideToolBar()
    }

    override fun onPause() {
        super.onPause()
        showToolBar()
    }
}
