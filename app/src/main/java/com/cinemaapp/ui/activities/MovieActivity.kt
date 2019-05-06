package com.cinemaapp.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import com.cinemaapp.R
import com.cinemaapp.data.models.Movie
import com.cinemaapp.databinding.MovieContainerLayoutBinding
import com.cinemaapp.ui.adapters.MovieContainerAdapter
import com.cinemaapp.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout


/*
* Created By mabrouk on 04/04/19
* CinemaApp
*/

class MovieActivity : BaseActivity() {
    lateinit var layoutBinding: MovieContainerLayoutBinding
    lateinit var adapter:MovieContainerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding=DataBindingUtil.setContentView(this, R.layout.movie_container_layout)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        SearchActivity.start(this)
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        adapter = MovieContainerAdapter(supportFragmentManager)
        layoutBinding.moviesViewPager.adapter = adapter
        layoutBinding.moviesViewPager.setOnTouchListener(View.OnTouchListener { v, event ->
            true
        })
        layoutBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                layoutBinding.moviesViewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}