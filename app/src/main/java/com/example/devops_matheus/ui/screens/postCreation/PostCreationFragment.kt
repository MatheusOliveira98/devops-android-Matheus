package com.example.devops_matheus.ui.screens.postCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.devops_matheus.R
import com.example.devops_matheus.ui.database.posts.PostDatabase
import com.example.devops_matheus.databinding.FragmentPostCreationBinding

class PostCreationFragment : Fragment()  {

    lateinit var binding : FragmentPostCreationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_creation, container, false)

        val appContext = requireNotNull(this.activity).application
        val dataSource = PostDatabase.getInstance(appContext).postDatabaseDao

        val viewModelFactory = PostCreationViewModelFactory(dataSource, appContext)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(PostCreationViewModel::class.java)

        binding.postCreationViewModel = viewModel

        binding.setLifecycleOwner (this)

        viewModel.saveEvent.observe(viewLifecycleOwner, Observer {
                saveEvent -> if(saveEvent){
                    viewModel.savePost(binding.editTextPostText.text.toString(), binding.editTextPostLink.text.toString())
                    view?.findNavController()?.navigate(R.id.action_postCreationFragment_to_postOverviewFragment)
                    viewModel.saveEventDone()
            }
        })

        return binding.root
    }
}