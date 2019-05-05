package com.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaapp.R
import com.cinemaapp.data.models.TvShowSeason
import com.cinemaapp.databinding.SeasonItemViewBinding


/*
* Created By mabrouk on 05/05/19
* CinemaApp
*/

class TvShowSeasonAdapter(val listener:SeasonListener) : RecyclerView.Adapter<TvShowSeasonAdapter.Holder>(){
   var seasons: ArrayList<TvShowSeason> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<SeasonItemViewBinding>(LayoutInflater.from(parent.context), R.layout.season_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = seasons.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(seasons[position])
    }

   fun  setData(data:ArrayList<TvShowSeason>){
       seasons=data
       notifyDataSetChanged()
   }


    inner class Holder(val viewBinding:SeasonItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(model:TvShowSeason){
            viewBinding.season=model
            viewBinding.root.setOnClickListener { listener.onSeasonClick(model) }
        }
    }

    interface SeasonListener{
        fun onSeasonClick(season: TvShowSeason)
    }
}