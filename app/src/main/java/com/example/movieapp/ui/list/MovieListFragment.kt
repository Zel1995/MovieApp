package com.example.movieapp.ui.list

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.MainActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieListBinding
import com.example.movieapp.domain.repository.MovieRetrofitRepositoryImpl
import com.example.movieapp.domain.router.MainRouter
import com.example.movieapp.domain.serviceRequest.CatchMovieService
import com.example.movieapp.snack
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    private var viewBinding: FragmentMovieListBinding? = null
    private val adapter =
        MovieCategoryAdapter {
            router.openMovieFragmentByMovie(it)
        }

    private lateinit var viewModel: MovieListViewModel

    @Inject
    lateinit var router: MainRouter

    @Inject
    lateinit var factory: MovieListViewModelFactory
    private val mainScope = MainScope()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMovieListBinding.bind(view)
        initRecyclerView()
        initViewModel()
        initFab()
        viewModel.fetchMovies()
        viewBinding?.swipeRefresh?.setOnRefreshListener {
            viewModel.fetchMovies()
            viewBinding?.swipeRefresh?.isRefreshing = false
        }

    }

    private fun initFab() {
        viewBinding?.fab?.snack(R.string.fab_clicked)
    }

    private fun initRecyclerView() {
        viewBinding?.moviesRv?.adapter = adapter
        viewBinding?.moviesRv?.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(
                MovieListViewModel::class.java
            )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? MainActivity)?.mainSubcomponent?.inject(this)
    }

    override fun onStart() {
        super.onStart()
        collectDataFromViewModel()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            viewModel.MovieResultReceiver(),
            IntentFilter(CatchMovieService.SERVICE_ACTION)
        )
    }

    private fun collectDataFromViewModel() {
        mainScope.launch {
            viewModel.movie.collect {
                adapter.setData(it)
            }
            viewModel.error.collect {
                Toast.makeText(requireContext(), it?:"null", Toast.LENGTH_SHORT).show()
                adapter.clearData()
            }
            viewModel.loading.collect {
                viewBinding?.progress?.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(viewModel.MovieResultReceiver())
    }
}

class MovieListViewModelFactory @Inject constructor(
    private val application: Application,
    private val repository: MovieRetrofitRepositoryImpl
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MovieListViewModel(application, repository) as T

}