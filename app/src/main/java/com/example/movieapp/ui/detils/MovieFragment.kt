package com.example.movieapp.ui.detils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.domain.Movie

private const val ARG_MOVIE = "MOVIE"

class MovieFragment : Fragment(R.layout.fragment_movie) {
    private var movie: Movie? = null
    private var viewBinding: FragmentMovieBinding? = null

    companion object {
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
        safeBinding.movieTitle.text = movie?.name.toString()
        safeBinding.movieContent.text = movie?.content.toString()
        movie?.imgRes?.let { safeBinding.movieImage.setImageResource(it) }
    }


}