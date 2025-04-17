package android.learn.list.domain

import android.learn.utils.Seconds
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val id: Long,
    val title: String,
    val duration: Seconds,
    val linkToMp3: String,
    val artistName: String,
    val albumImageLink: String
): Parcelable
