package com.movieapp.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.movieapp.R

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        movieDetailsViewModel =
            ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
    }
}