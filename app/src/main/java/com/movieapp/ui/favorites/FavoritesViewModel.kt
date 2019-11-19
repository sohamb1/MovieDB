package com.movieapp.ui.favorites

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.movieapp.data.Poster
import com.movieapp.database.MovieDBRepository
import com.movieapp.database.MovieDatabase
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieDBRepository
    val favoriteMoviesList: LiveData<List<Poster>>

    init {
        val moviesDao = MovieDatabase.getDatabase(application).movieDao()
        repository = MovieDBRepository(moviesDao)
        favoriteMoviesList = repository.favoriteMovies
    }

    fun insert(movie: Poster) = viewModelScope.launch {
        repository.insert(movie)
    }

    fun delete(movie: Poster) = viewModelScope.launch {
        repository.delete(movie)
    }
}