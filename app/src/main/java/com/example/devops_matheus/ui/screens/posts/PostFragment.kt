package com.example.devops_matheus.ui.screens.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.devops_matheus.R
import com.example.devops_matheus.databinding.FragmentPostBinding
import com.example.devops_matheus.ui.database.posts.PostDatabase

class PostFragment: Fragment() {

    private lateinit var binding: FragmentPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)

        val appContext = requireNotNull(this.activity).application
        val dataSource = PostDatabase.getInstance(appContext).postDatabaseDao

        val viewModelFactory = PostViewModelFactory(dataSource, appContext)
        val viewModel: PostViewModel by viewModels{viewModelFactory}

        binding.posts = viewModel

        binding.setLifecycleOwner(this)

        return binding.root
    }
}