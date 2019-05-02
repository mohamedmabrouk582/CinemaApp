package com.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaapp.R
import com.cinemaapp.data.models.TvShow
import com.cinemaapp.databinding.TvShowItemViewBinding


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

class TvShowListAdapter(val listener:TvShowListener) : PagedListAdapter<TvShow,TvShowListAdapter.Holder>(callBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<TvShowItemViewBinding>(LayoutInflater.from(parent.context), R.layout.tv_show_item_view,parent,false)
         return Holder(viewBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    companion object {
        val callBack = object  : DiffUtil.ItemCallback<TvShow>(){
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean = newItem.id==oldItem.id
            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean = newItem==oldItem

        }
    }

    inner class Holder(var viewBinding: TvShowItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(model : TvShow){
            viewBinding.tvShow=model
            viewBinding.root.setOnClickListener { listener.onTvShowClick(model)}
        }
    }

    interface TvShowListener{
        fun onTvShowClick(item : TvShow)
    }
}