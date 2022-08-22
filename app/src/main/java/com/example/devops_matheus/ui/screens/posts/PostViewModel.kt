package com.example.devops_matheus.ui.screens.posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devops_matheus.ui.database.comments.CommentDatabaseDao
import com.example.devops_matheus.ui.database.comments.Comment
import com.example.devops_matheus.ui.database.posts.PostDatabaseDao
import com.example.devops_matheus.ui.database.posts.Post
import kotlinx.coroutines.launch
import timber.log.Timber
import android.content.DialogInterface

import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.R

import android.view.LayoutInflater
import android.view.View


class PostViewModel(val database: PostDatabaseDao, val databaseCom: CommentDatabaseDao, application: Application): AndroidViewModel(application) {

    private var _isReaction: Boolean
    val isReaction: Boolean
        get() = _isReaction

    private var _selectedPost = MutableLiveData<Post>()
    val selectedPost: LiveData<Post>
        get() = _selectedPost

    private val _saveEventComment = MutableLiveData<Boolean>()
    val saveEventComment: LiveData<Boolean>
        get() = _saveEventComment

    private val _updateEventComment = MutableLiveData<Boolean>()
    val updateEventComment: LiveData<Boolean>
        get() = _updateEventComment

    private val _replyEventComment = MutableLiveData<Boolean>()
    val replyEventComment: LiveData<Boolean>
        get() = _replyEventComment

    private var _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    var uComment = Comment()

    init {
        Timber.i("PostViewModel init is called")
        _saveEventComment.value = false
        _isReaction = false
    }

    fun setPost(postId: Long) {
        viewModelScope.launch {
            _selectedPost.value = database.get(postId)
            _comments.value = databaseCom.getAllCommentsFromPost(selectedPost.value!!.postId)
        }
    }

    //fun setComments(postId: Long) {
    //    _comments.value = databaseCom.getAllCommentsFromPostLive(postId)
    //}

    fun saveCommentClick() {
        _saveEventComment.value = true
    }

    fun saveCommentDone() {
        _saveEventComment.value = false
    }

    fun updateCommentClick(comment: Comment) {
        _updateEventComment.value = true
        uComment = comment

    }

    fun updateCommentDone() {
        _updateEventComment.value = false
    }

    fun replyCommentClick(comment: Comment) {
        _replyEventComment.value = true
        uComment = comment
        _isReaction = true
    }

    fun replyCommentDone() {
        _replyEventComment.value = false
        _isReaction = false
    }

    fun saveComment(postId: Long, commentText: String, commentUser: String, reaction: Long?) {
        viewModelScope.launch {
            val com = Comment()
            com.commentPostId = postId
            com.commentText = commentText
            com.commentUser = commentUser
            com.commentIsReaction = isReaction
            com.commentReaction = reaction
            saveCommentToDatabase(com)
            _comments.value = databaseCom.getAllCommentsFromPost(selectedPost.value!!.postId)
        }
    }

    suspend fun saveCommentToDatabase(newComment: Comment) {
        databaseCom.insert(newComment)
    }


    fun updateComment() {
        viewModelScope.launch {
            databaseCom.update(uComment)
            _comments.value = databaseCom.getAllCommentsFromPost(selectedPost.value!!.postId)
        }
    }

    fun onClickVerwijder(comment: Comment) {
        viewModelScope.launch {
            databaseCom.delete(comment)
            _comments.value = databaseCom.getAllCommentsFromPost(selectedPost.value!!.postId)
        }
    }
}