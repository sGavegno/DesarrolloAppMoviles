package com.example.apppokedex.database

import androidx.room.*
import com.example.apppokedex.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id")
    fun fetchAllUsers(): MutableList<User?>?

    @Query("SELECT * FROM users WHERE id = :id")
    fun fetchUserById(id: Int?): User?

    @Query("SELECT * FROM users WHERE email = :email")
    fun fetchUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE UserName = :UserName")
    fun fetchUserByUserName(UserName: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun delete(user: User)
}