package com.example.devops_matheus.ui.screens.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.devops_matheus.databinding.CommentListItemBinding
import com.example.devops_matheus.ui.database.comments.Comment
import com.example.devops_matheus.ui.login.CredentialsManager
import timber.log.Timber

class CommentAdapter(val clickListener: CommentListener, val userId: String, val pVM: PostViewModel): ListAdapter<Comment, ViewHolder>(CommentDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
//        holder.itemView.setOnClickListener {
//            clickListener.onClick(item)
//        }
        holder.bind(clickListener, item, userId, pVM)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder(val binding: CommentListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(clickListener: CommentListener, item: Comment, userId: String, pVM: PostViewModel) {
        if(item.commentIsReaction) {
            binding.commentUser.text = "${item.commentUser} reply to ${pVM.uComment.commentUser}"
        } else {
            binding.commentUser.text = item.commentUser
        }
        binding.commentText.text = item.commentText

        Timber.i(item.commentUser + " vs " + userId)
        if(item.commentUser != userId) {
            binding.bewerkButton.isGone = true
            binding.verwijderButton.isGone = true
        }
        binding.comment = item
        binding.clickListener = clickListener
        binding.postViewModel = pVM
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CommentListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

class CommentDiffCallback: DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return (oldItem.commentId == newItem.commentId && oldItem.commentText == newItem.commentText)
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}

class CommentListener(val clickListener: (comment: Comment)->Unit) {
    fun onClick(comment: Comment) = clickListener(comment)
}