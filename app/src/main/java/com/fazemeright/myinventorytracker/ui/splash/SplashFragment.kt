package com.fazemeright.myinventorytracker.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazemeright.myinventorytracker.databinding.FragmentSplashBinding
import com.fazemeright.myinventorytracker.utils.hideToolBar
import com.fazemeright.myinventorytracker.utils.showToolBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSplashBinding.inflate(inflater).apply {
            lifecycleOwner = this@SplashFragment
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideToolBar()
        binding.viewModel = viewModel
        viewModel.isUserSignedIn.observe(requireActivity()) { userIsSignedIn ->
            val action = if (userIsSignedIn) {
                viewModel.syncLocalAndCloudData(requireActivity().application)
                SplashFragmentDirections.actionSplashFragmentToItemListFragment()
            } else {
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            }
            findNavController().navigate(action)
        }
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
