package com.movieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.R
import com.movieapp.utils.PaginationScrollListener

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var recyclerAdapter: RecyclerAdapter
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

        gridLayoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        homeViewModel.fetchMovies(page)
        homeViewModel.popularMoviesLiveData.observe(this, Observer {
            if(page == 1) {
                recyclerAdapter = RecyclerAdapter(it, activity)
                recyclerView.adapter = recyclerAdapter
            } else if(page > 1) {
                recyclerAdapter.addData(it)
            }
            progressBar.visibility = View.GONE
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