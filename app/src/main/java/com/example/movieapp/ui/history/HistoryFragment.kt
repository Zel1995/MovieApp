package com.example.movieapp.ui.history

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHistoryBinding
import com.example.movieapp.domain.repository.HistoryLocalRepositoryImpl
import com.example.movieapp.storage.MovieDataBase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var viewBinding: FragmentHistoryBinding
    private val adapter = HistoryAdapter()

    lateinit var factory: HistoryViewModelFactory
    private val mainScope = MainScope()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        factory = HistoryViewModelFactory(
            HistoryLocalRepositoryImpl(
                Room.databaseBuilder(
                    requireActivity().application,
                    MovieDataBase::class.java,
                    "MovieDataBase"
                ).fallbackToDestructiveMigration().build().getMovieDao()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentHistoryBinding.bind(view)
        initRecyclerView()
        initViewModel()
        initToolbar()
        viewModel.fetchHistory()
        viewBinding.swipeRefresh.setOnRefreshListener {
            viewModel.fetchHistory()
            viewBinding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initToolbar() {
        viewBinding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_history -> {
                    viewModel.deleteHistory()
                    true
                }
                else -> {
                    Toast.makeText(requireContext(), "Delete error", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), factory).get(HistoryViewModel::class.java)
        mainScope.launch {
            viewModel.history.collect {
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initRecyclerView() {
        viewBinding.historyRv.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
        viewBinding.historyRv.adapter = adapter
    }
}