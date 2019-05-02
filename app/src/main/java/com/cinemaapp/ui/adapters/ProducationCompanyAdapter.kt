package com.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaapp.R
import com.cinemaapp.data.models.ProductionCompanies
import com.cinemaapp.databinding.ProducationItemViewBinding


/*
* Created By mabrouk on 21/04/19
* CinemaApp
*/

class ProducationCompanyAdapter : RecyclerView.Adapter<ProducationCompanyAdapter.Holder>() {
    var data:ArrayList<ProductionCompanies> = ArrayList()

    fun setProducation(data: ArrayList<ProductionCompanies>){
        this.data=data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<ProducationItemViewBinding>(LayoutInflater.from(parent.context), R.layout.producation_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    inner class Holder(val viewBinding: ProducationItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data:ProductionCompanies){
            viewBinding.producation=data
        }
    }
}