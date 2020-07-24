package com.sebix.kotlinsingleton.repository

import androidx.lifecycle.LiveData
import com.sebix.kotlinsingleton.api.MyRetrofitBuilder
import com.sebix.kotlinsingleton.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object Repository {

    var job: CompletableJob? = null

    fun getUser(userId: String): LiveData<User> {
        job = Job()
        return object : LiveData<User>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val user=MyRetrofitBuilder.apiService.getUser(userId)
                        withContext(Main){
                            value=user
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }

}