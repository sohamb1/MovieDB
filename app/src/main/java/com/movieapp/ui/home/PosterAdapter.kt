package com.movieapp.ui.home

import android.app.ActivityOptions
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

class PosterAdapter(data: MutableList<Poster>, activity: FragmentActivity?) :
    RecyclerView.Adapter<PosterAdapter.Holder>() {

    private var movies: MutableList<Poster> = data
    private var activity: FragmentActivity? = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(movies.get(position), activity)
    }

    class Holder(v: View): RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var image: ImageView = v.findViewById(R.id.movieImage)

        fun bind(movie: Poster, activity: FragmentActivity?) {
            Picasso.get().load(AppConstants.MOVIE_PHOTO_URL + movie.poster_path).centerCrop().fit().into(image)
            view.setOnClickListener {
                val intent: Intent = Intent(activity, MovieDetailsActivity::class.java)
                intent.putExtra("id", movie.id)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    fun addData(listItems: MutableList<Poster>) {
        val size = movies.size
        this.movies.addAll(listItems)
        val sizeNew = this.movies.size
        notifyItemRangeChanged(size, sizeNew)
    }
}