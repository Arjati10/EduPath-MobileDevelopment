package com.example.edupath.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Community(
    val namaCommunity: String, // Add this property
    val lokasi: String,
    val type: String,
    val detail: String,
    val photoGroup: String,
    val communityId: String,
    val members: List<String>
):Parcelable {
    constructor() : this("", "", "", "", "", "", listOf("")) {}
}