package com.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaapp.R
import com.cinemaapp.data.models.Movie
import com.cinemaapp.databinding.MovieItemViewBinding


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

class MovieAdapter(val listener: MovieListener) : PagedListAdapter<Movie,MovieAdapter.Holder>(callBack){
    companion object {
        val callBack = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = newItem.id==oldItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<MovieItemViewBinding>(LayoutInflater.from(parent.context), R.layout.movie_item_view,parent,false)
        return  Holder(viewBinding)
     }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    inner class Holder(val viewBinding: MovieItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(model : Movie){
            viewBinding.movie=model
            viewBinding.root.setOnClickListener { listener.onClickMovie(model) }
        }
    }

     interface MovieListener{
        fun onClickMovie(item:Movie)
    }
}