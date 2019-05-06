package com.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaapp.R
import com.cinemaapp.data.enums.MediaType
import com.cinemaapp.data.models.SearchResult
import com.cinemaapp.databinding.SearchItemViewBinding


/*
* Created By mabrouk on 06/05/19
* CinemaApp
*/

class SearchAdapter (val listener:SearchListener) : PagedListAdapter<SearchResult,SearchAdapter.Holder>(callBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<SearchItemViewBinding>(LayoutInflater.from(parent.context), R.layout.search_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    companion object{
        val callBack = object : DiffUtil.ItemCallback<SearchResult>(){
            override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean = newItem.id==oldItem.id

            override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean = newItem==oldItem

        }
    }

    inner class Holder(val viewBinding:SearchItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(model:SearchResult){
            viewBinding.searchItem=model
            viewBinding.root.setOnClickListener {
                if (model.media_type==MediaType.movie)
                   listener.onMovieClick(model)
                 else
                    listener.onTvClick(model)
            }
        }
    }

    interface SearchListener{
        fun onMovieClick(item:SearchResult)
        fun onTvClick(item:SearchResult)
    }
}