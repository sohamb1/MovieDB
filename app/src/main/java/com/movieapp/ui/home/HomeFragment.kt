package com.movieapp.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.R
import com.movieapp.utils.PaginationScrollListener
import com.movieapp.utils.Utilities
import okhttp3.internal.Util

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var posterAdapter: PosterAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        val progressBar: ProgressBar = root.findViewById(R.id.progress_circular)
        val retry: TextView = root.findViewById(R.id.text_retry)

        gridLayoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        if(Utilities.isOnline(activity)) {
            homeViewModel.fetchMovies(page)
            retry.visibility = View.GONE
        } else {
            Toast.makeText(activity, "No internet Present, Please enable internet connection", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            retry.visibility = View.VISIBLE
        }

        retry.setOnClickListener{
            if(Utilities.isOnline(activity)) {
                homeViewModel.fetchMovies(page)
                progressBar.visibility = View.VISIBLE
                retry.visibility = View.GONE
            } else
                Toast.makeText(activity, "No internet Present, Please enable internet connection", Toast.LENGTH_SHORT).show()
        }

        homeViewModel.popularMoviesLiveData.observe(this, Observer {
            if(page == 1) {
                posterAdapter = PosterAdapter(it, activity)
                recyclerView.adapter = posterAdapter
            } else if(page > 1) {
                posterAdapter.addData(it)
            }
            progressBar.visibility = View.GONE
            retry.visibility = View.GONE
            isLoading = false
        })

        recyclerView.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                homeViewModel.fetchMovies(++page)
            }
        })

        return root
    }
}