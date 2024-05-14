package com.example.moviedb.presentation.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.core.domain.model.Movie
import com.example.moviedb.R
import com.example.moviedb.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class DetailActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding
    private var isFavorite = false
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        supportActionBar?.hide()

        setContentView(binding.root)

        movie = DetailActivityArgs.fromBundle(intent.extras as Bundle).movie

        showDetailMovie(movie)

        binding.ivBack.setOnClickListener {
            finish()
        }


    }

    private fun showDetailMovie(movie: Movie?) {
        movie?.let { domain ->
            binding.tvTitle.text = domain.title
            binding.tvDate.text = domain.releaseDate
            binding.tvOverviewContent.text = domain.overview
            binding.tvVote.text = String.format(Locale.US, "%.2f", domain.voteAverage)
            binding.tvPopularity.text = String.format(Locale.US, "%.0f", domain.popularity)
            Glide.with(this@DetailActivity)
                .load("https://image.tmdb.org/t/p/original/${domain.posterPath}")
                .placeholder(com.example.core.R.drawable.image_not_found)
                .into(binding.ivPoster)
            Glide.with(this@DetailActivity)
                .load("https://image.tmdb.org/t/p/original/${domain.backdropPath}")
                .placeholder(com.example.core.R.drawable.image_not_found)
                .into(binding.ivBackdrop)

            binding.ivPoster.contentDescription = movie.title


            detailViewModel.checkFavorite(domain.id).observe(this) {
                isFavorite = it
                setStatusFavorite(isFavorite)

            }

            setStatusFavorite(isFavorite)
            binding.ivFavorite.setOnClickListener {
                insertDeleteFavorite()
                setStatusFavorite(isFavorite)

            }

        }

    }

    private fun insertDeleteFavorite() {
        if (!isFavorite) {
            movie?.let { detailViewModel.setFavorite(it) }
            isFavorite = !isFavorite
        } else {
            movie?.let { detailViewModel.deleteFavorite(it) }
            isFavorite = !isFavorite
        }
    }


    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.baseline_favorite_24
                )
            )
        } else {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.baseline_favorite_border_24
                )
            )
        }
    }
}