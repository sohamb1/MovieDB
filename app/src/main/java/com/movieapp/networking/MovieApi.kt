package com.movieapp.networking

import com.movieapp.data.MovieResponse
import com.movieapp.data.PosterResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    fun getPopularMovieAsync(@Query("page") page: Int): Deferred<Response<PosterResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetailsAsync(@Path("movie_id") id: Int): Deferred<Response<MovieResponse>>
}