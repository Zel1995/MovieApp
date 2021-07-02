package com.example.movieapp.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.storage.MovieEntity

class HistoryAdapter() :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original"
    }

    private val historyList = mutableListOf<MovieEntity>()

    fun setData(historyToSet: List<MovieEntity>) {
        historyList.apply {
            clear()
            addAll(historyToSet)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        with(holder) {
            Glide.with(image).load(BASE_IMAGE_URL + item.posterPath).into(image)
            historyDate.text = item.creationDate
            movieName.text = item.title
        }
    }


    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.history_img)
        val historyDate: TextView = itemView.findViewById(R.id.history_date)
        val movieName: TextView = itemView.findViewById(R.id.movie_name)
    }
}