package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemMovieBinding
import com.example.core.domain.model.Movie


class MoviePagingAdapter: PagingDataAdapter<Movie, MoviePagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(private val binding: ItemMovieBinding, private val onItemClick: ((Movie) -> Unit)?): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Movie){

                binding.apply {
                    tvTitle.text = data.title

                    Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/original/${data.posterPath}")
                        .placeholder(R.drawable.image_not_found)
                        .into(ivMovie)
                    ivMovie.contentDescription = data.title
                    ivForeground.contentDescription = data.title


                }

            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount) {
            VIEW_TYPE_MOVIE
        } else {
            VIEW_TYPE_LOADING
        }
    }

    companion object {
        const val VIEW_TYPE_MOVIE = 1
        const val VIEW_TYPE_LOADING = 2

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}