package me.gyanesh.hdp.data.model

import androidx.annotation.Keep

@Keep
data class Video(
    val id: Int,
    val title: String,
    val videoId: String,
    val thumbnail: String
)