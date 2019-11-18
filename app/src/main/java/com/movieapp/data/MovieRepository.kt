package com.movieapp.data

import com.movieapp.networking.MovieApi

class MovieRepository(private val api : MovieApi) : BaseRepository() {

    private var posterResponse: PosterResponse? = null
    private var movieResponse: MovieResponse? = null

    suspend fun getPopularMovies(page: Int) : MutableList<Poster>?{
        posterResponse = safeApiCall(
                call = { api.getPopularMovieAsync(page).await() },
                errorMessage = "Error Fetching Popular Movies"
            )
            return posterResponse?.results?.toMutableList();
        }

    suspend fun getMovieDetails(id: Int) : MutableList<Movie>?{
        movieResponse = safeApiCall(
            call = { api.getMovieDetailsAsync(id).await() },
            errorMessage = "Error Fetching Movie Details"
        )
        return movieResponse?.results?.toMutableList();
    }
}