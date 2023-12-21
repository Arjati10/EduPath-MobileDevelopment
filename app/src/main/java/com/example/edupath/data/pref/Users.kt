package com.example.edupath.data.pref

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(
    val name: String ?= null,
    val number: String ?= null,
    val domisili: String ?= null,
    val sekolah: String ?= null,
    val imageProfile: String ?= null
){

}
