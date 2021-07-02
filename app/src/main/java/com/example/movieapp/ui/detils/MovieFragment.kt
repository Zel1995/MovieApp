package com.example.movieapp.ui.detils

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.HistoryLocalRepositoryImpl
import com.example.movieapp.domain.repository.NoteLocalRepositoryImpl
import com.example.movieapp.storage.MovieDataBase
import com.example.movieapp.storage.MovieEntity
import com.example.movieapp.storage.NoteEntity
import com.example.movieapp.ui.history.HistoryViewModel
import com.example.movieapp.ui.history.HistoryViewModelFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_MOVIE = "MOVIE"

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private var movie: Movie? = null
    private var viewBinding: FragmentMovieBinding? = null

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var dataBase: MovieDataBase
    private lateinit var moviesViewModelFactory: MoviesViewModelFactory
    private lateinit var historyFactory: HistoryViewModelFactory
    private lateinit var historyViewModel: HistoryViewModel
    val mainScope = MainScope()

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataBase = Room.databaseBuilder(
            requireActivity().application,
            MovieDataBase::class.java,
            "MovieDataBase"
        ).fallbackToDestructiveMigration().build()
        historyFactory = HistoryViewModelFactory(
            HistoryLocalRepositoryImpl(
                dataBase.getMovieDao()
            )
        )
        moviesViewModelFactory =
            MoviesViewModelFactory(NoteLocalRepositoryImpl(dataBase.getNoteDao()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMovieBinding.bind(view)
        initHistoryViewModel()
        initMoviesViewModel()
        initViews()
        initMenu()

    }

    private fun initMoviesViewModel() {
        moviesViewModel = ViewModelProvider(
            requireActivity(),
            moviesViewModelFactory
        ).get(MoviesViewModel::class.java)
        mainScope.launch {
            moviesViewModel.note.collect {
                viewBinding?.noteTv?.text = it?.note
            }
        }
        val safeMovie = movie ?: return
        moviesViewModel.fetchNote(safeMovie.id)

    }


    private fun initMenu() {
        val safeBinding = viewBinding ?: return
        safeBinding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.add_item) {
                val movieDialogFragment = MovieDialogFragment()
                movieDialogFragment.show(requireActivity().supportFragmentManager, "note dialog")
                movieDialogFragment.addCallback {
                    viewBinding?.noteTv?.text = it
                    moviesViewModel.addNote(NoteEntity(0, movie!!.id, it))
                }
                return@setOnMenuItemClickListener true
            }
            false
        }
    }

    private fun initHistoryViewModel() {
        historyViewModel =
            ViewModelProvider(requireActivity(), historyFactory).get(HistoryViewModel::class.java)
        val safeMovie = movie ?: return
        historyViewModel.addMovieEntity(createMovieEntityFromMovie(safeMovie))
    }

    private fun createMovieEntityFromMovie(movie: Movie): MovieEntity {
        val movieEntity = movie.let {
            val simpleDateFormat = SimpleDateFormat("HH:mm:ss - dd/MM/yyyy ");
            val dateText = simpleDateFormat.format(Date(System.currentTimeMillis()));
            MovieEntity(
                0,
                it.id,
                it.adult,
                it.title,
                it.overview,
                it.posterPath,
                it.releaseDate,
                it.voteAverage,
                dateText
            )
        }
        return movieEntity
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