package me.gyanesh.hdp.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val image: String
) : Serializable