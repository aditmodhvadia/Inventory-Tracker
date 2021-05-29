package com.fazemeright.myinventorytracker.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.FragmentLoginBinding
import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.ui.base.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val RC_SIGN_IN: Int = 1001
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var gso: GoogleSignInOptions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewmodel = viewModel

        binding.btnLogin.setOnClickListener {
//            TODO: Show Loading
            viewModel.onLoginClicked(
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }

        binding.btnGoogleSignIn.setOnClickListener {
            configureGoogleSignIn()

            googleSignIn()
        }

        viewModel.loginResult.observe(requireActivity(), { result ->
//            TODO: Hide Loading
            if (result is Result.Success) {
                viewModel.syncLocalAndCloudData()
//                open(ItemListActivity::class.java)
//                finish()
            } else if (result is Result.Error) {
                Timber.e(result.exception)
//                showToast(result.msg)
            }
        })
        return binding.root
    }

    private fun googleSignIn() {
        val signInIntent = GoogleSignIn.getClient(requireActivity(), gso).signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    private fun configureGoogleSignIn() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Timber.d("firebaseAuthWithGoogle:%s", account.id)
                viewModel.signInWithToken(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Timber.e(e, "Google sign in failed")
                // ...
            }
        }
    }
}