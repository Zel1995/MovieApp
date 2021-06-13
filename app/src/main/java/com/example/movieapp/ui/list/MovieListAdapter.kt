package com.example.movieapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.domain.Movie

typealias ItemClicked = (movie: Movie) -> Unit?

class MovieListAdapter(private val itemClicked: ItemClicked) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<Movie>()

    fun setData(dataForSet: List<Movie>) {
        val callback = MovieDiffutilCallBack(data, dataForSet)
        val result = DiffUtil.calculateDiff(callback)
        data.apply {
            clear()
            addAll(dataForSet)
        }
        notifyDataSetChanged()
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListAdapter.MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MovieViewHolder)?.bind(data[position])

    }

    override fun getItemCount(): Int = data.size


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_item)
        val imgIcon: ImageView = itemView.findViewById(R.id.img_item)
        val content: TextView = itemView.findViewById(R.id.content_item)

        init {
            itemView.setOnClickListener { itemClicked(data[adapterPosition]) }
        }

        fun bind(movie: Movie) {
            title.text = movie.name
            imgIcon.setImageResource(movie.imgRes)
            content.text = movie.content
        }
    }

    inner class MovieDiffutilCallBack(val oldList: List<Movie>, val newList: List<Movie>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList.get(oldItemPosition).content == newList.get(newItemPosition).content
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList.get(oldItemPosition) == newList.get(newItemPosition)
        }
    }
}
