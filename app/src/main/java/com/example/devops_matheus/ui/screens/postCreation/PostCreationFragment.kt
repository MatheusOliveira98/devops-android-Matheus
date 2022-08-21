package com.example.devops_matheus.ui.screens.postCreation

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.devops_matheus.R
import com.example.devops_matheus.ui.database.posts.PostDatabase
import com.example.devops_matheus.databinding.FragmentPostCreationBinding
import com.example.devops_matheus.ui.login.CredentialsManager
import android.os.Build


class PostCreationFragment : Fragment() {

    lateinit var binding: FragmentPostCreationBinding
    var chosenImage: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_post_creation, container, false)

        val appContext = requireNotNull(this.activity).application
        val dataSource = PostDatabase.getInstance(appContext).postDatabaseDao

        val viewModelFactory = PostCreationViewModelFactory(dataSource, appContext)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(PostCreationViewModel::class.java)

        binding.postCreationViewModel = viewModel

        binding.setLifecycleOwner(this)

        binding.addImageButton.setOnClickListener {
            //imageGallery()
            selectImageFromGallery()
        }

        var userId = CredentialsManager.getUserId(requireContext())
        viewModel.saveEvent.observe(viewLifecycleOwner, Observer { saveEvent ->
            if (saveEvent) {
                viewModel.savePost(
                    binding.editTextPostText.text.toString(),
                    binding.editTextPostLink.text.toString(), userId!!, chosenImage
                )
                view?.findNavController()
                    ?.navigate(R.id.action_postCreationFragment_to_postOverviewFragment)
                viewModel.saveEventDone()
            }
        })

        return binding.root
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
            binding.imageViewPostImage.setImageURI(uri)
        }
    }
}