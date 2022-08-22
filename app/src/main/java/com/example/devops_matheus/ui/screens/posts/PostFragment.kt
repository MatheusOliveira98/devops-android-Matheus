package com.example.devops_matheus.ui.screens.posts

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.devops_matheus.databinding.FragmentPostBinding
import com.example.devops_matheus.ui.database.comments.CommentDatabase
import com.example.devops_matheus.ui.database.posts.PostDatabase
import com.example.devops_matheus.ui.login.CredentialsManager
import timber.log.Timber
import androidx.appcompat.app.AlertDialog
import com.example.devops_matheus.R

import com.google.android.material.textfield.TextInputEditText




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
        val dataSourceCom = CommentDatabase.getInstance(appContext).commentDatabaseDao

        val viewModelFactory = PostViewModelFactory(dataSource, dataSourceCom, appContext)
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

        var userId = CredentialsManager.getUserId(requireContext())
        viewModel.saveEventComment.observe(viewLifecycleOwner, Observer { saveEventComment ->
            if (saveEventComment) {
                viewModel.saveComment(
                    viewModel.selectedPost.value!!.postId,
                    binding.postAddComment.text.toString(),
                    userId!!,
                    null
                )
                //view?.findNavController()
                //    ?.navigate(R.id.action_postCreationFragment_to_postOverviewFragment)
                //updateUI(viewModel)
                viewModel.saveCommentDone()
            }
        })

        val adapter = CommentAdapter(CommentListener {
                comment -> Toast.makeText(context, "${comment.commentId}", Toast.LENGTH_LONG).show()
            //postId -> Navigation.findNavController(requireActivity(), R.id.postFragment)
            //viewModel.displayCommentDetails(post)
        }, userId!!, viewModel)

        viewModel.comments.observe(viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })

        binding.commentList.adapter = adapter

        viewModel.updateEventComment.observe(viewLifecycleOwner, Observer { updateEventComment ->
            if (updateEventComment) {
                updateCommentDialog(viewModel)
                viewModel.updateCommentDone()
            }
        })

        viewModel.replyEventComment.observe(viewLifecycleOwner, Observer { replyEventComment ->
            if (replyEventComment) {
                replyCommentDialog(viewModel, userId!!)
                viewModel.replyCommentDone()
            }
        })

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

    fun updateCommentDialog(viewModel: PostViewModel) {
        val inflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.comment_alert_dialog, null)
        val editedComment: TextInputEditText = alertLayout.findViewById(R.id.tiet_comment)
        editedComment.setText(viewModel.uComment.commentText)
        Timber.i(viewModel.uComment.commentText)

        val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        alert.setView(alertLayout)

        alert.setCancelable(false)
        alert.setNegativeButton("Cancel") { dialog, which ->
        }

        alert.setPositiveButton("Done") { dialog, which ->
            viewModel.uComment.commentText = editedComment.text.toString()
            viewModel.updateComment()
        }
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }

    fun replyCommentDialog(viewModel: PostViewModel, userId: String) {
        val inflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.comment_alert_dialog, null)
        val editedComment: TextInputEditText = alertLayout.findViewById(R.id.tiet_comment)

        val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        alert.setView(alertLayout)

        alert.setCancelable(false)
        alert.setNegativeButton("Cancel") { dialog, which ->
        }

        alert.setPositiveButton("Done") { dialog, which ->
            viewModel.saveComment(
                viewModel.selectedPost.value!!.postId,
                editedComment.text.toString(),
                userId!!,
                viewModel.uComment.commentId
            )
        }
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }
}