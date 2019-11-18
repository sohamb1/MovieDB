package com.movieapp.networking

import com.movieapp.AppConstants

object ApiFactory {

    val movieApi: MovieApi = RetrofitFactory.retrofit(AppConstants.MOVIEDB_BASE_URL).create(MovieApi::class.java)
}