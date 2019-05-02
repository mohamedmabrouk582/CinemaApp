package com.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaapp.R
import com.cinemaapp.data.models.MovieTrailer
import com.cinemaapp.databinding.MovieTrailerItemViewBinding


/*
* Created By mabrouk on 21/04/19
* CinemaApp
*/

class MovieTrailerAdapter(val listener:TrailerListener) : RecyclerView.Adapter<MovieTrailerAdapter.Holder>(){

     var data:ArrayList<MovieTrailer> = ArrayList()

    fun setTrailers(data:ArrayList<MovieTrailer>){
        this.data=data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<MovieTrailerItemViewBinding>(LayoutInflater.from(parent.context), R.layout.movie_trailer_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data = data[position])
    }

    inner class Holder(val viewBinding: MovieTrailerItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data:MovieTrailer){
            viewBinding.movieTrailer=data
            viewBinding.root.setOnClickListener { listener.onTrailerClick(data) }
        }
    }

    interface TrailerListener{
        fun onTrailerClick(item:MovieTrailer)
    }
}