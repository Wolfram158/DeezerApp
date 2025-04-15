package android.learn.list.presentation.adapter

import android.learn.list.domain.Track
import androidx.recyclerview.widget.DiffUtil

class TrackDiffUtilCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}