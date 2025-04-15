package android.learn.found.domain

import android.learn.utils.Seconds

data class Track(
    val id: Long,
    val title: String,
    val duration: Seconds,
    val linkToMp3: String,
    val artistName: String,
    val albumImageLink: String
)
