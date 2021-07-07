package com.example.movieapp.ui.history

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.movieapp.MainActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHistoryBinding
import com.example.movieapp.domain.repository.HistoryLocalRepositoryImpl
import com.example.movieapp.storage.MovieDataBase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryFragment : Fragment(R.layout.fragment_history) {


    private lateinit var viewModel: HistoryViewModel
    private lateinit var viewBinding: FragmentHistoryBinding
    private val adapter = HistoryAdapter()

    @Inject
    lateinit var factory: HistoryViewModelFactory
    private val mainScope = MainScope()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? MainActivity)?.mainSubcomponent?.inject(this)
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
                    Snackbar.make(viewBinding.root,"Do you wanna clear history?",Snackbar.LENGTH_SHORT).setAction("Yes"){
                        viewModel.deleteHistory()
                    }
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

                adapter.setData(it.asReversed())
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initRecyclerView() {
        viewBinding.historyRv.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        viewBinding.historyRv.adapter = adapter
    }
}