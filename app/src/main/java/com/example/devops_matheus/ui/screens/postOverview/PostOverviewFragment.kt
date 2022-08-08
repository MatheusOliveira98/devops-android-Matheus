package com.example.devops_matheus.ui.screens.postOverview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.devops_matheus.R
import com.example.devops_matheus.databinding.FragmentPostOverviewBinding
import com.example.devops_matheus.ui.database.posts.PostDatabase

class PostOverviewFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentPostOverviewBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_post_overview, container, false
        )

        val appContext = requireNotNull(this.activity).application
        val dataSource = PostDatabase.getInstance(appContext).postDatabaseDao

        val viewModelFactory = PostOverviewViewModelFactory(dataSource, appContext)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(PostOverviewViewModel::class.java)

        binding.postOverviewViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = PostAdapter(PostListener {
            postId -> Toast.makeText(context, "${postId}", Toast.LENGTH_LONG).show()
        })
        binding.postList.adapter = adapter

        viewModel.posts.observe(viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })

        binding.postCreateButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.postCreationFragment)
        )

        return binding.root
    }
}