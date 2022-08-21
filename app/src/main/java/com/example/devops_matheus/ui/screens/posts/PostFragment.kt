package com.example.devops_matheus.ui.screens.posts

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.devops_matheus.R
import com.example.devops_matheus.databinding.FragmentPostBinding
import com.example.devops_matheus.ui.database.posts.Post
import com.example.devops_matheus.ui.database.posts.PostDatabase
import com.example.devops_matheus.ui.screens.postOverview.PostOverviewFragmentDirections
import timber.log.Timber

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

        Timber.i("Post fragment running")
        val postId =
            PostFragmentArgs.fromBundle(requireArguments()).selectedPost
        viewModel.setPost(postId)

        binding.postViewModel = viewModel

        viewModel.selectedPost.observe(viewLifecycleOwner, Observer {
                post -> updateUI(viewModel)

        })

        binding.postLink.setOnClickListener {
                gotoUrl(viewModel.selectedPost.value!!.postLink)
        }

        binding.setLifecycleOwner(this)

        return binding.root
    }

    private fun gotoUrl(s: String) {
        Timber.i("Link clicked")
        val uri: Uri = Uri.parse(s)
        val finalUri: Uri
        if(uri.scheme == null) {
            finalUri = Uri.parse("https://"+s)
        } else finalUri = uri
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = finalUri


        try {
            if(intent.resolveActivity(requireContext().packageManager) != null) {
                Timber.i("activity not null")
                startActivity(intent)
            } else {
                Timber.i("activity null")
            }
            //startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Timber.e("Activity error:" + e.message.toString())
        }
    }

    fun updateUI(viewModel: PostViewModel) {
        var post = viewModel.selectedPost.value!!
        Timber.i(post.postId.toString())
        binding.postUser.text = post.postUser
        binding.postImage.setImageBitmap(post.postImage)
        binding.postLink.text = post.postLink
        binding.postText.text = post.postText

    }
}