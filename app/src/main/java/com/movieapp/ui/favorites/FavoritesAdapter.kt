package com.movieapp.ui.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.AppConstants
import com.movieapp.R
import com.movieapp.data.Poster
import com.movieapp.ui.details.MovieDetailsActivity
import com.squareup.picasso.Picasso

class FavoritesAdapter internal constructor(
    activity: FragmentActivity?
) : RecyclerView.Adapter<FavoritesAdapter.MovieHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(activity)
    private var movies = emptyList<Poster>()
    private var activity: FragmentActivity? = activity

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.movieImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val current = movies[position]
        Picasso.get().load(AppConstants.MOVIE_PHOTO_URL + current.poster_path).centerCrop().fit().into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra("id", current.id)
            intent.putExtra("isFavorite", true)
            activity?.startActivity(intent)
            activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    internal fun setWords(words: List<Poster>) {
        this.movies = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size
}