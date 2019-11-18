package com.movieapp.data

data class Movie (
    val id: Int,
    val poster_path: String,
    val backdrop_path: String,
    val budget: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val revenue: Int,
    val runtime: Int
)

data class MovieResponse(
    val results: List<Movie>
)