package com.example.movieapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.domain.Movie

typealias ItemClicked = (movie: Movie) -> Unit

class ListAdapter(private val itemClicked: ItemClicked? = null) :
    RecyclerView.Adapter<ListAdapter.MovieViewHolder>() {

    private val data = mutableListOf<Movie>()

    fun setData(dataForSet:List<Movie>){
        data.apply {
            clear()
            addAll(dataForSet)
        }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data.get(position))

    }

    override fun getItemCount(): Int = data.size


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_item)
        val imgIcon: ImageView = itemView.findViewById(R.id.img_item)
        val content: TextView = itemView.findViewById(R.id.content_item)

        init {
            itemView.setOnClickListener{itemClicked?.invoke(data[adapterPosition])}
        }
        fun bind(movie: Movie) {
            title.text = movie.name
            imgIcon.setImageResource(movie.imgRes)
            content.text = movie.content
        }
    }

}
