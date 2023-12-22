package com.example.edupath.view

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.glide(url: String) {
    Glide.with(this.context).load(url).into(this)
}