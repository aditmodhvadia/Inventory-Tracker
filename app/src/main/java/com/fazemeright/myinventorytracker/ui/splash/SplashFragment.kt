package com.fazemeright.myinventorytracker.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.FragmentSplashBinding
import com.fazemeright.myinventorytracker.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(layoutInflater, container, savedInstanceState)

        binding.viewModel = viewModel

        viewModel.isUserSignedIn.observe(requireActivity(), { userIsSignedIn ->
            if (userIsSignedIn) {
                viewModel.syncLocalAndCloudData()
//                TODO: Load ItemListActivity
//                open(ItemListActivity::class.java)
            } else {
//                TODO: Load LoginActivity
//                open(LoginActivity::class.java)
            }
//            TODO: Remove SplashFragment from stack
//            finish()
            findNavController().popBackStack(R.id.splashFragment, true)
        })
        return binding.root
    }
}
