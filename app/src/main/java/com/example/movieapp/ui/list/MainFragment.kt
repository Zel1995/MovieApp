package com.example.movieapp.ui.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.MainFragmentBinding
import com.example.movieapp.domain.router.RouterHolder

class MainFragment : Fragment(R.layout.main_fragment) {
    private var viewBinding: MainFragmentBinding? = null
    private val adapter =
        ListAdapter { (activity as? RouterHolder)?.router?.openMovieFragmentByMovie(it) }

    private lateinit var viewModel: MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = MainFragmentBinding.bind(view)
        initRecyclerView()
        initViewModel()
        viewModel.fetchMovies()

    }

    private fun initRecyclerView() {
        viewBinding?.moviesRv?.adapter = adapter
        viewBinding?.moviesRv?.layoutManager = GridLayoutManager(requireActivity(), 2)
    }

    private fun initViewModel() {
        //val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.movieLiveDada.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        viewModel.errorLiveDada.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        /*    viewModel.loadingLiveDada.observe(viewLifecycleOwner){
                if(it){
                *//*    recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE*//*
            }else{*//*
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE*//*
            }
        }*/

    }

}