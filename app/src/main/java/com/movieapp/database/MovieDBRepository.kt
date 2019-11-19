package com.movieapp.database

import androidx.lifecycle.LiveData
import com.movieapp.data.Poster

class MovieDBRepository(private val movieDao: MovieDao) {

    val favoriteMovies: LiveData<List<Poster>> = movieDao.getFavoriteMovies()

    suspend fun insert(poster: Poster) {
        movieDao.insert(poster)
    }

    suspend fun delete(poster: Poster) {
        movieDao.delete(poster)
    }
}