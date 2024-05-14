package com.example.moviedb.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.domain.model.Movie
import com.example.core.ui.LoadingStateAdapter
import com.example.core.ui.MoviePagingAdapter
import com.example.core.ui.MoviePagingAdapter.Companion.VIEW_TYPE_LOADING
import com.example.moviedb.databinding.FragmentHomeBinding
import com.example.moviedb.utils.calculateSpanCount
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: MoviePagingAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MoviePagingAdapter().apply {
            onItemClick = { movie: Movie ->
                val action = HomeFragmentDirections.actionNavigationHomeToDetailActivity(movie)
                findNavController().navigate(action)
            }
        }



        val layoutManager =
            GridLayoutManager(requireActivity(), calculateSpanCount(requireActivity())).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter.getItemViewType(position) == VIEW_TYPE_LOADING) {
                            spanCount
                        } else {
                            1
                        }
                    }
                }
            }

        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )
        binding.rvMovies.layoutManager = layoutManager


        homeViewModel.movies.observe(viewLifecycleOwner) { pagingData ->
            Log.d("HomeFragment", "PagingData received")
            adapter.submitData(lifecycle, pagingData)
        }

        adapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner) { loadState ->
            if (loadState.append is LoadState.Error && adapter.itemCount < 1) {
                binding.emptyLy.mainEmpty.visibility = View.VISIBLE
            } else if (loadState.append is LoadState.NotLoading && adapter.itemCount < 1) {
                binding.emptyLy.mainEmpty.visibility = View.VISIBLE
            } else {
                binding.emptyLy.mainEmpty.visibility = View.GONE
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }


    }

}