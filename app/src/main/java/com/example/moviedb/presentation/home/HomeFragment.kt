package com.example.moviedb.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.core.domain.Movie
import com.example.moviedb.databinding.FragmentHomeBinding
import com.example.moviedb.presentation.DetailActivity
import com.example.moviedb.core.ui.LoadingStateAdapter
import com.example.moviedb.core.ui.MoviePagingAdapter
import com.example.moviedb.core.ui.MoviePagingAdapter.Companion.VIEW_TYPE_LOADING
import com.example.moviedb.utils.calculateSpanCount
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private  lateinit var binding: FragmentHomeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val layoutManager = GridLayoutManager(requireActivity(), calculateSpanCount())
//
//        binding.rvMovies.layoutManager = layoutManager

        val adapter = MoviePagingAdapter()


        val layoutManager = GridLayoutManager(requireActivity(), calculateSpanCount(requireActivity()))
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // If it's the loading state, occupy all spans, otherwise just 1 span
                return if (adapter.getItemViewType(position) == VIEW_TYPE_LOADING) {
                    layoutManager.spanCount
                } else {
                    1
                }
            }
        }

        adapter.onItemClick = {movie: Movie ->
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE,movie)
            startActivity(intent)
        }

        binding.rvMovies.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )

        binding.rvMovies.layoutManager = layoutManager

        homeViewModel.movies.observe(viewLifecycleOwner) { pagingData ->
            // Submit the PagingData to the adapter
            Log.d("test1", "test2")
            adapter.submitData(lifecycle, pagingData)
//            if (adapter.itemCount < 1){
//                val toast = Toast.makeText(context, "This is a toast message", Toast.LENGTH_SHORT).show()
//            }


        }

        adapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner) { loadState ->
//                if (loadState.append is LoadState.Loading) {
//                    // Show loading spinner
//                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
//                } else if(loadState.append is LoadState.NotLoading && adapter.itemCount < 1){
//                    Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
//                }

            when (loadState.append) {
                is LoadState.Loading -> {
                    // Show loading spinner
                }
                is LoadState.Error -> {
                    // Show error message

                    binding.emptyLy.mainEmpty.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE


                }
                is LoadState.NotLoading -> {
                    // Hide loading spinner and errormessage
                    binding.emptyLy.mainEmpty.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.retry()
            binding.swipeRefreshLayout.isRefreshing = false
        }

    }


//    private fun calculateSpanCount(): Int {
//        val displayMetrics = resources.displayMetrics
//        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
//        val desiredItemWidthDp = 180 // Desired width of each grid item in dp
//        return (screenWidthDp / desiredItemWidthDp).toInt()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
}