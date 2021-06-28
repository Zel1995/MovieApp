package com.example.movieapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieCategory

typealias ItemClicked = (movie: Movie) -> Unit?

class MovieCategoryAdapter(private val itemClicked: ItemClicked) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<MovieCategory>()
    private val pool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
    fun setData(dataForSet: List<MovieCategory>?) {
        val callback = MovieDiffutilCallBack(data, dataForSet)
        val result = DiffUtil.calculateDiff(callback)
        data.apply {
            clear()
            addAll(dataForSet?: mutableListOf())
        }
        notifyDataSetChanged()
        result.dispatchUpdatesTo(this)
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieCategoryAdapter.MovieCategoryViewHolder =
        MovieCategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        (holder as? MovieCategoryViewHolder)?.apply {
            title.text = item.name
            with(moviesList) {
                setRecycledViewPool(pool)
                val linearLayoutManager =
                    LinearLayoutManager(moviesList.context, LinearLayoutManager.HORIZONTAL, false)
                layoutManager = linearLayoutManager
                val moviesAdapter = MoviesAdapter(item.movies) { movie -> itemClicked(movie) }
                adapter = moviesAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        linearLayoutManager.findLastVisibleItemPosition().takeIf { it != -1 }?.let {
                        }
                    }
                })
            }
        }

    }

    override fun getItemCount(): Int = data.size


    inner class MovieCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.category_title)
        val moviesList: RecyclerView = itemView.findViewById(R.id.movies_list)
    }

    inner class MovieDiffutilCallBack(
        val oldList: List<MovieCategory>?,
        val newList: List<MovieCategory>?
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList?.size ?: 0
        }

        override fun getNewListSize(): Int {
            return newList?.size ?: 0
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList?.get(oldItemPosition)?.name == newList?.get(newItemPosition)?.name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList?.get(oldItemPosition)  == newList?.get(newItemPosition)
        }
    }

}
