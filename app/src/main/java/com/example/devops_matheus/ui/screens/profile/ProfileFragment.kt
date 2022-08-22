package com.example.devops_matheus.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.devops_matheus.R
import com.example.devops_matheus.databinding.FragmentProfileBinding
import com.example.devops_matheus.ui.database.users.UserDatabase
import com.example.devops_matheus.ui.login.CredentialsManager
import timber.log.Timber

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding

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
        val userId = CredentialsManager.getUserId(requireContext())

        val appContext = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(appContext).userDatabaseDao

        val viewModelFactory = ProfileViewModelFactory(dataSource, userId!!, appContext)
        val viewModel: ProfileViewModel by viewModels{viewModelFactory}
        Timber.i(userId)

        binding.profile = viewModel

        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
                user -> updateUI(viewModel)
        })

        viewModel.editEvent.observe(viewLifecycleOwner, Observer { editEvent ->
            if (editEvent) {
                this.findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment()
                )
                viewModel.editEventDone()
                updateUI(viewModel)
            }
        })

        binding.setLifecycleOwner(this)

        return binding.root
    }

    private fun updateUI(viewModel: ProfileViewModel) {
        var user = viewModel.currentUser.value!!
        binding.textviewProfileName.text = user.userName
        binding.profileAvatar.setImageBitmap(user.userAvatar)
    }
}