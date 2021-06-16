package com.example.movieapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.domain.Movie

class MoviesAdapter(private val movies: List<Movie>, val itemClicked: (movie: Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesAdapter.MovieViewHolder, position: Int) {
        val item = movies[position]
        with(holder) {
            title.text = item.name
            imgIcon.setImageResource(item.imgRes)
        }
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_item)
        val imgIcon: ImageView = itemView.findViewById(R.id.img_item)

        init {
            itemView.setOnClickListener { itemClicked(movies[adapterPosition]) }
        }


        }
    }
}