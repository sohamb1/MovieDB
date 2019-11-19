package com.movieapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movieapp.data.Movie
import com.movieapp.data.MovieRepository
import com.movieapp.networking.ApiFactory
import com.movieapp.ui.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel

class MovieDetailsViewModel : BaseViewModel() {

    private val repository : MovieRepository = MovieRepository(ApiFactory.movieApi)

    val movieDetails = MutableLiveData<Movie>()

    fun fetchDetails(id: Int){
        scope.async {
            val details = repository.getMovieDetails(id)
            movieDetails.postValue(details)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
}