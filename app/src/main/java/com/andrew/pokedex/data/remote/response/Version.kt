package com.andrew.pokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class Version(
    val name: String,
    val url: String
)