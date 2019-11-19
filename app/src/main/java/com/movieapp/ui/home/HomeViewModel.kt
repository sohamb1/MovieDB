package com.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movieapp.data.MovieRepository
import com.movieapp.data.Poster
import com.movieapp.networking.ApiFactory
import com.movieapp.ui.BaseViewModel
import kotlinx.coroutines.*

class HomeViewModel : BaseViewModel() {

    private val repository : MovieRepository = MovieRepository(ApiFactory.movieApi)

    val popularMoviesLiveData = MutableLiveData<MutableList<Poster>>()

    fun fetchMovies(page: Int){
        scope.async {
            val popularMovies = repository.getPopularMovies(page)
            popularMoviesLiveData.postValue(popularMovies)
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text
}