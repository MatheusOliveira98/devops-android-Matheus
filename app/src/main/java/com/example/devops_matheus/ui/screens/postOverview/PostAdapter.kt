package com.example.devops_matheus.ui.screens.postOverview

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.devops_matheus.databinding.PostListItemBinding
import com.example.devops_matheus.ui.database.posts.Post

class PostAdapter(val clickListener: PostListener): ListAdapter<Post, ViewHolder>(PostDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
//        holder.itemView.setOnClickListener {
//            clickListener.onClick(item)
//        }
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder(val binding: PostListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(clickListener: PostListener, item: Post) {
        binding.postText.text = item.postText
        binding.postUser.text = item.postUser
        binding.postLink.text = item.postLink
        binding.postImage.setImageBitmap(item.postImage)

        binding.post = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PostListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

class PostDiffCallback: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

class PostListener(val clickListener: (post: Post)->Unit) {
    fun onClick(post: Post) = clickListener(post)
}