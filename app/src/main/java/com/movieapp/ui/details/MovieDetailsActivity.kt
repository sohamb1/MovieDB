package com.movieapp.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.movieapp.AppConstants
import com.movieapp.R
import com.squareup.picasso.Picasso
import android.view.MenuItem
import android.widget.*
import com.movieapp.data.Poster
import com.movieapp.ui.favorites.FavoritesViewModel
import com.movieapp.utils.Utilities
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Deferred


class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var favoritesViewModel: FavoritesViewModel
    private var posterPath: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val movieId = intent.getIntExtra("id", 0)
        val isFavorite = intent.getBooleanExtra("isFavorite", false)
        action_button_favorites.text = if (isFavorite) {
            action_button_favorites.setBackgroundColor(resources.getColor(R.color.colorAccent))
            getString(R.string.remove_favorite)
        } else {
            action_button_favorites.setBackgroundColor(resources.getColor(R.color.colorGreen))
            getString(R.string.add_favorite)
        }

        val poster: ImageView = findViewById(R.id.poster_image)
        val title: TextView = findViewById(R.id.title)
        val overview: TextView = findViewById(R.id.overview)
        val revenue: TextView = findViewById(R.id.revenue)
        val runtime: TextView = findViewById(R.id.runtime)
        val budget: TextView = findViewById(R.id.budget)
        val originalLanguage: TextView = findViewById(R.id.original_language)
        val progressBar: ProgressBar = findViewById(R.id.progress_circular)
        val actionButtonFavorites: Button = findViewById(R.id.action_button_favorites)

        movieDetailsViewModel =
            ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        favoritesViewModel =
            ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

        movieId.let {
            if (Utilities.isOnline(this)) {
                movieDetailsViewModel.fetchDetails(it)
            } else {
                Toast.makeText(
                    this,
                    "No internet Present, Please enable internet connection",
                    Toast.LENGTH_SHORT
                ).show()
                progressBar.visibility = View.GONE
            }
            movieDetailsViewModel.movieDetails.observe(this, Observer {
                progressBar.visibility = View.GONE
                actionButtonFavorites.visibility = View.VISIBLE

                Picasso.get().load(AppConstants.MOVIE_PHOTO_URL + it.poster_path).into(poster)
                posterPath = it.poster_path
                title.text = it.original_title
                overview.text = it.overview
                if (it.revenue == 0 || it.revenue == null)
                    revenue.visibility = View.GONE
                else
                    revenue.text = "Revenue: $ ${it.revenue}"

                if (it.runtime == 0 || it.runtime == null)
                    runtime.visibility = View.GONE
                else
                    runtime.text = "Runtime: ${it.runtime}"

                if (it.budget == 0 || it.budget == null)
                    budget.visibility = View.GONE
                else
                    budget.text = "Budget: $ ${it.budget}"

                originalLanguage.text = "Language: ${it.original_language}"

            })
        }

        actionButtonFavorites.setOnClickListener {
            Log.d("ad", "soham click")
            if (actionButtonFavorites.text.equals(getString(R.string.add_favorite))) {
                favoritesViewModel.insert(Poster(id = movieId, poster_path = posterPath))
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
                actionButtonFavorites.visibility = View.GONE
            } else if (actionButtonFavorites.text.equals(getString(R.string.remove_favorite))) {
                favoritesViewModel.delete(Poster(id = movieId, poster_path = posterPath))
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
                actionButtonFavorites.visibility = View.GONE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}