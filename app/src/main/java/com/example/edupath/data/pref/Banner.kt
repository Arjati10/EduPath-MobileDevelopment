package com.example.edupath.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    val title : String,
    val detail : String,
    val photoB : String,
) : Parcelable
