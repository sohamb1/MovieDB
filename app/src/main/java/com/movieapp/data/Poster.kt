package com.movieapp.data

data class Poster (
    val id: Int,
    val poster_path: String
)

data class PosterResponse(
    val results: List<Poster>
)