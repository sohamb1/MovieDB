package com.movieapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.movieapp.data.Poster

@Dao
interface MovieDao {

    @Query("SELECT * from movies")
    fun getFavoriteMovies(): LiveData<List<Poster>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(poster: Poster)

    @Delete
    suspend fun delete(poster: Poster)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}