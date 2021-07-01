package com.example.movieapp.ui.detils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.domain.Movie

private const val ARG_MOVIE = "MOVIE"

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private var movie: Movie? = null
    private var viewBinding: FragmentMovieBinding? = null

    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original"

        fun newInstance(movie: Movie) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(ARG_MOVIE) as Movie?
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMovieBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        val safeBinding = viewBinding ?: return
        with(safeBinding) {
            movieTitle.text = movie?.title.toString()
            movieContent.text = movie?.overview.toString()
            ratingTv.text = movie?.voteAverage.toString()
            val ratingForSet = movie?.voteAverage?.div(2) ?: 0
            ratingBar.rating = ratingForSet.toFloat()

            val releaseDate: String =
                getString(R.string.release_date) + " " + movie?.releaseDate.toString()
            realiseDate.text = releaseDate
            Glide.with(movieImage)
                .load(BASE_IMAGE_URL + movie?.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_home)
                .fitCenter()
                .centerInside()
                .optionalCenterInside()
                .into(movieImage)
        }
    }


}