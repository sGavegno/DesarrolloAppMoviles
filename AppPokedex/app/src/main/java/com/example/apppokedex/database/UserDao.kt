package com.example.apppokedex.database

import androidx.room.*
import com.example.apppokedex.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY idUser")
    fun fetchAllUsers(): MutableList<User?>?

    @Query("SELECT * FROM users WHERE idUser = :idUser")
    fun fetchUserById(idUser: Int): User?

    @Query("SELECT * FROM users WHERE email = :email")
    fun fetchUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE idUser = :UserName")
    fun fetchUserByUserName(UserName: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun delete(user: User)
}