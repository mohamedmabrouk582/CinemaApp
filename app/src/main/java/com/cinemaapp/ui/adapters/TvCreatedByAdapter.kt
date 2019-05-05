package com.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaapp.R
import com.cinemaapp.data.models.TvShowCreated
import com.cinemaapp.databinding.CreatedByItemViewBinding
import com.cinemaapp.databinding.NetworkItemViewBinding


/*
* Created By mabrouk on 05/05/19
* CinemaApp
*/

class TvCreatedByAdapter (val listener:CreatedByListener) : RecyclerView.Adapter<TvCreatedByAdapter.Holder>(){
    var createdBy:ArrayList<TvShowCreated> = ArrayList()

    fun setData(data:ArrayList<TvShowCreated>){
        this.createdBy=data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<CreatedByItemViewBinding>(LayoutInflater.from(parent.context), R.layout.created_by_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = createdBy.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(createdBy[position])
    }

    inner class Holder(val viewBinding: CreatedByItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(model:TvShowCreated){
            viewBinding.createdBy=model
        }
    }

    interface CreatedByListener{
        fun onCreatedByClick(item:TvShowCreated)
    }
}