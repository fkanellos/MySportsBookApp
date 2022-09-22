package com.example.mysportsbookapp.data.api.dao

import com.google.gson.annotations.SerializedName

data class E(
    @SerializedName("d")
    val gameInfo: String,
    @SerializedName("i")
    val id: String,
    val sh: String,
    @SerializedName("si")
    val sportsId: String,
    @SerializedName("tt")
    val eventStartingTime: Long,
)
