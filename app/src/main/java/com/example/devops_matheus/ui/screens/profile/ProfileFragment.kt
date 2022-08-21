package com.example.devops_matheus.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.devops_matheus.R
import com.example.devops_matheus.databinding.FragmentProfileBinding
import timber.log.Timber
import com.example.devops_matheus.ui.login.CredentialsManager
import com.example.devops_matheus.ui.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var user: UserProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        val appContext = requireNotNull(this.activity).application

        Timber.i(user.getId())
        //val viewModelFactory = ProfileViewModelFactory(user, appContext)
        //val viewModel: ProfileViewModel by viewModels{viewModelFactory}

        //binding.currentUser = viewModel

        binding.setLifecycleOwner(this)

        return binding.root
    }
}