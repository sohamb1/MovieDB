package com.movieapp.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Poster (
    @PrimaryKey
    val id: Int,
    @NonNull
    val poster_path: String?
)

data class PosterResponse(
    val results: List<Poster>
)