package com.example.devops_matheus

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.devops_matheus.ui.database.posts.Post
import com.example.devops_matheus.ui.database.posts.PostDatabase
import com.example.devops_matheus.ui.database.posts.PostDatabaseDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class PostDatabaseTest {

    private lateinit var postDao: PostDatabaseDao
    private lateinit var db: PostDatabase

    @Before
    fun createDb() {
        Log.i("before", "running before")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, PostDatabase::class.java).allowMainThreadQueries().build()
        postDao = db.postDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun createAndGetPost() = runBlocking {
        val post = Post(1,"Test Post","")
        postDao.insert(post)
        val lastPost = postDao.getLastPost()
        assertEquals(lastPost?.postText, "Test Post")
    }
}