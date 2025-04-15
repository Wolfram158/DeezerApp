package android.learn.list.presentation.adapter

import android.learn.list.databinding.ItemTrackBinding
import android.learn.list.domain.Track
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load

class TracksAdapter(private val onGotoTrackListener: OnGotoTrackListener) :
    ListAdapter<Track, TrackViewHolder>(
        TrackDiffUtilCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        ItemTrackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).also { return TrackViewHolder(it) }
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = currentList[position]

        with(holder.binding) {
            trackName.text = item.title
            trackAuthor.text = item.artistName
            trackImage.load(item.albumImageLink)
            root.setOnClickListener {
                onGotoTrackListener
            }
        }
    }

    interface OnGotoTrackListener {
        fun onGotoTrack()
    }
}