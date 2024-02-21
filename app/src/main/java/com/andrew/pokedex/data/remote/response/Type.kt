package com.andrew.pokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class Type(
    val slot: Int,
    val type: TypeX
)