package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.R
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.UserDao
import com.example.apppokedex.entities.User

class FragmentLoginViewModel : ViewModel() {

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    lateinit var user : LiveData<User>
    fun getUserData(userName : String) {

        //db = AppDatabase.getInstance()
        //userDao = db?.userDao()

        // Dummy call to pre-populate db
        //userDao?.fetchAllUsers()

        //val userAux = userDao?.fetchUserByUserName(userName)

    }

}
