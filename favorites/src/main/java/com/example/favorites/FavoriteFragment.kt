package com.example.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.domain.model.Movie
import com.example.core.ui.MovieListAdapter
import com.example.favorites.databinding.FragmentFavoriteBinding
import com.example.favorites.di.favoriteModule
import com.example.moviedb.utils.calculateSpanCount
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        loadKoinModules(favoriteModule)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MovieListAdapter()
        val layoutManager =
            GridLayoutManager(requireActivity(), calculateSpanCount(requireActivity()))
        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = adapter

        adapter.onItemClick = { movie: Movie ->
            val action = FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity(movie)
            findNavController().navigate(action)
        }

//        binding.emptyLyi.mainEmpty.visibility = View.GONE

        favoriteViewModel.favoriteMovie.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)

            if (movies.isNotEmpty()) {
                binding.emptyLy.mainEmpty.visibility = View.GONE
            } else {
                binding.emptyLy.mainEmpty.visibility = View.VISIBLE
            }

        }

    }

}