package com.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaapp.R
import com.cinemaapp.data.models.TvNetWorks
import com.cinemaapp.data.models.TvShowSeason
import com.cinemaapp.databinding.NetworkItemViewBinding


/*
* Created By mabrouk on 05/05/19
* CinemaApp
*/

class TvNetworkAdapter (val listener:NetworkListener) : RecyclerView.Adapter<TvNetworkAdapter.Holder>(){
    var networks:ArrayList<TvNetWorks> = ArrayList()

    fun setData(data:ArrayList<TvNetWorks>){
        networks=data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<NetworkItemViewBinding>(LayoutInflater.from(parent.context), R.layout.network_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = networks.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(networks[position])
    }

    inner class Holder(val viewBinding: NetworkItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(model:TvNetWorks){
            viewBinding.network=model
            viewBinding.root.setOnClickListener { listener.onNetworkClick(model) }
        }
    }

    interface NetworkListener{
        fun onNetworkClick(season: TvNetWorks)
    }
}