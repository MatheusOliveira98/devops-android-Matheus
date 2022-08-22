package com.example.devops_matheus.ui.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.devops_matheus.ui.database.posts.Post
import com.example.devops_matheus.ui.database.users.User

@Entity(primaryKeys = ["userId", "postId"], tableName = "userpostclicked_table")
data class UserPostClicked(
    val userId: String,
    val postId: Long
)

data class PostClickedByUser(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "postId",
        associateBy = Junction(UserPostClicked::class)
    )
    val posts: List<Post>
)