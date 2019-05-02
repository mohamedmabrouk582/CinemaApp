package com.cinemaapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.cinemaapp.R
import com.cinemaapp.databinding.MovieDetailsActivityBinding
import com.cinemaapp.databinding.MovieDetailsLayoutBinding
import com.cinemaapp.ui.base.BaseActivity
import com.cinemaapp.utils.Constans


/*
* Created By mabrouk on 09/04/19
* CinemaApp
*/

class MovieDetailsActivity : BaseActivity() {
    lateinit var layoutBinding: MovieDetailsActivityBinding
    lateinit var contrallor:NavController

    companion object {
        fun start(context: Context,movieId:Long){
            val intent=Intent(context,MovieDetailsActivity::class.java)
            intent.putExtra(Constans.MOVIEID,movieId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding=DataBindingUtil.setContentView(this, R.layout.movie_details_activity)
        initView()
    }
    override fun initView() {
      contrallor=Navigation.findNavController(this,R.id.movie_nav_graph)
    }

   override fun inject(){
    }
}