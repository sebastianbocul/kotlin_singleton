package com.sebix.kotlinsingleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sebix.kotlinsingleton.model.User
import com.sebix.kotlinsingleton.repository.Repository

class MainViewModel: ViewModel() {
    private val _userId: MutableLiveData<String> = MutableLiveData()
    val user:LiveData<User> = Transformations.switchMap(_userId){
        Repository.getUser(it)
    }

    fun setUserId(userId:String){
        val update=userId
        if(_userId.value==update){
            return
        }
        _userId.value=update
    }

    fun cancelJobs(){
        Repository.cancelJobs()
    }
}