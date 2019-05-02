package com.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaapp.R
import com.cinemaapp.data.models.MovieCast
import com.cinemaapp.databinding.CastItemViewBinding


/*
* Created By mabrouk on 21/04/19
* CinemaApp
*/

class MovieCastAdapter : RecyclerView.Adapter<MovieCastAdapter.Holder> (){
     var data: ArrayList<MovieCast> = ArrayList()

     fun setMovieCast(cast:ArrayList<MovieCast>){
      this.data=cast
       notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<CastItemViewBinding>(LayoutInflater.from(parent.context), R.layout.cast_item_view,parent,false);
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    inner class Holder(var viewBinding: CastItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){

        fun bind(data:MovieCast){
              viewBinding.movieCast = data
        }
    }
}