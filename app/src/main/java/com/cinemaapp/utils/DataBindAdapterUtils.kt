package com.cinemaapp.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.bumptech.glide.Glide


/*
* Created By mabrouk on 15/03/19
* KotilnApp
*/
class DataBindAdapterUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("app:loadImage")
        fun loadImage(view: ImageView, url: String?) {
            Glide.with(view)
                .load(url)
                .into(view)
        }
    }
}
