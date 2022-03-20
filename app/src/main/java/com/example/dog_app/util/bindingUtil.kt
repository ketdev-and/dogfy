package com.example.dog_app.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object bindingUtil {
    @JvmStatic
    @BindingAdapter("breedImg")
    fun loadImage (view : ImageView, url : String){
        Glide.with(view.context).load(url).into(view)
    }
}
