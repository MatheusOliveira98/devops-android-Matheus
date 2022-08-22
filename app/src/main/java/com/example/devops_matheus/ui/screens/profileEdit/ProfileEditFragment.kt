package com.example.devops_matheus.ui.screens.profileEdit

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.devops_matheus.R
import com.example.devops_matheus.databinding.FragmentProfileEditBinding
import com.example.devops_matheus.ui.database.users.UserDatabase
import com.example.devops_matheus.ui.login.CredentialsManager

class ProfileEditFragment: Fragment() {

    private lateinit var binding: FragmentProfileEditBinding
    var chosenImage: Bitmap? = null

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
            R.layout.fragment_profile_edit,
            container,
            false
        )
        val userId = CredentialsManager.getUserId(requireContext())

        val appContext = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(appContext).userDatabaseDao

        val viewModelFactory = ProfileEditViewModelFactory(dataSource, userId!!, appContext)
        val viewModel: ProfileEditViewModel by viewModels{viewModelFactory}

        binding.profileEdit = viewModel

        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
                user -> updateUI(viewModel)
        })

        viewModel.saveEvent.observe(viewLifecycleOwner, Observer { saveEvent ->
            if (saveEvent) {
                viewModel.saveUser(
                    chosenImage,
                    binding.profileEditUserName.text.toString()
                )
                viewModel.saveEventDone()
                this.findNavController().navigate(
                    ProfileEditFragmentDirections.actionProfileEditFragmentToProfileFragment()
                )
            }
        })

        binding.profileEditAvatarButton.setOnClickListener {
            selectImageFromGallery()
        }

        binding.setLifecycleOwner(this)

        return binding.root
    }

    private fun updateUI(viewModel: ProfileEditViewModel) {
        var user = viewModel.currentUser.value!!
        binding.profileEditProfileName.text = user.userName
        binding.profileEditAvatar.setImageBitmap(user.userAvatar)
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            if(Build.VERSION.SDK_INT < 28) {
                chosenImage = MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver,
                    uri
                )
            } else {
                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                chosenImage = ImageDecoder.decodeBitmap(source)
            }
            binding.profileEditAvatar.setImageURI(uri)
        }
    }
}