package android.learn.deezer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.learn.deezer.databinding.FragmentTrackBinding
import android.learn.found.presentation.FoundTracksViewModel
import android.learn.list.domain.Track
import android.learn.list.presentation.ViewModelFactory
import android.learn.track.presentation.TrackViewModel
import android.learn.utils.formatTime
import android.media.session.PlaybackState
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.service.notification.StatusBarNotification
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.properties.Delegates

class TrackFragment : Fragment() {
    private var _binding: FragmentTrackBinding? = null
    private val binding: FragmentTrackBinding
        get() = _binding ?: throw RuntimeException("FragmentTrackBinding is null")

    private lateinit var tracks: Array<Track>
    private var position by Delegates.notNull<Int>()

    @Inject
    lateinit var controllerFuture: ListenableFuture<MediaController>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TrackViewModel::class.java]
    }

    private var _controller: MediaController? = null
    private val controller: MediaController
        get() = _controller ?: throw RuntimeException("MediaController is null")

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)

        super.onAttach(context)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tracks = it.getParcelableArray(TRACKS) as Array<Track>
            position = it.getInt(POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val track = tracks[position]

        binding.trackName.text = track.title
        binding.artistName.text = track.artistName
        binding.spentTime.text = getString(R.string.start_time)
        binding.albumImage.load(track.albumImageLink)

        f()
    }

    private fun f() {
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                binding.spentTime.text = controller.currentPosition.toString()
                handler.postDelayed(this, 1000)
            }
        }

        binding.playButton.setOnClickListener {
            controllerFuture.addListener({
                _controller = controllerFuture.get()
                if (controller.currentMediaItem == null) {
                    lifecycleScope.launch {
                        //data.data.forEach {
                        loadMediaItem(tracks[position].linkToMp3.toUri(), tracks[position])
                        //}
                        controller.prepare()
                        binding.totalTime.text = formatTime(controller.duration)
                        controller.play()
                        binding.playButton.visibility = View.GONE
                        binding.pauseButton.visibility = View.VISIBLE
                        controller.addListener(
                            object : Player.Listener {
                                override fun onPlaybackStateChanged(playbackState: Int) {
                                    super.onPlaybackStateChanged(playbackState)
                                    if (playbackState == PlaybackState.STATE_PLAYING) {
                                        handler.post(runnable)
                                    } else {
                                        handler.removeCallbacks(runnable)
                                    }
                                }
                            }
                        )
                    }
                    controller.addListener(
                        object : Player.Listener {
                            override fun onPlaybackStateChanged(playbackState: Int) {
                                super.onPlaybackStateChanged(playbackState)
                                if (playbackState == Player.STATE_ENDED) {
                                }
                            }
                        })
                } else {
                    controller.play()
                    binding.playButton.visibility = View.GONE
                    binding.pauseButton.visibility = View.VISIBLE
                }
            }, MoreExecutors.directExecutor())
        }

        binding.pauseButton.setOnClickListener {
            controllerFuture.addListener({
                _controller = controllerFuture.get()
                if (controller.currentMediaItem == null) {
                    lifecycleScope.launch {
                        //data.data.forEach {
                        loadMediaItem(tracks[position].linkToMp3.toUri(), tracks[position])
                        //}
                        controller.prepare()
                        binding.spentTime.text = formatTime(controller.duration)
                        controller.play()
                        binding.playButton.visibility = View.VISIBLE
                        binding.pauseButton.visibility = View.GONE
                        controller.addListener(
                            object : Player.Listener {
                                override fun onPlaybackStateChanged(playbackState: Int) {
                                    super.onPlaybackStateChanged(playbackState)
                                    if (playbackState == PlaybackState.STATE_PLAYING) {
                                        handler.post(runnable)
                                    } else {
                                        handler.removeCallbacks(runnable)
                                    }
                                }
                            }
                        )
                    }
                    controller.addListener(
                        object : Player.Listener {
                            override fun onPlaybackStateChanged(playbackState: Int) {
                                super.onPlaybackStateChanged(playbackState)
                                if (playbackState == Player.STATE_ENDED) {
                                }
                            }
                        })
                } else {
                    controller.pause()
                    binding.playButton.visibility = View.VISIBLE
                    binding.pauseButton.visibility = View.GONE
                }
            }, MoreExecutors.directExecutor())
        }

//        controllerFuture.addListener({
//            _controller = controllerFuture.get()
//            controller.currentMediaItem
//            lifecycleScope.launch {
//                //data.data.forEach {
//                loadMediaItem(data.data[0].linkToMp3.toUri(), data.data[0])
//                //}
//                controller.prepare()
//                binding.spentTime.text = formatTime(controller.duration)
//                controller.play()
//                controller.addListener(
//                    object : Player.Listener {
//                        override fun onPlaybackStateChanged(playbackState: Int) {
//                            super.onPlaybackStateChanged(playbackState)
//                            if (playbackState == PlaybackState.STATE_PLAYING) {
//                                handler.post(runnable)
//                            } else {
//                                handler.removeCallbacks(runnable)
//                            }
//                        }
//                    }
//                )
//            }
//            controller.addListener(
//                object : Player.Listener {
//                    override fun onPlaybackStateChanged(playbackState: Int) {
//                        super.onPlaybackStateChanged(playbackState)
//                        if (playbackState == Player.STATE_ENDED) {
//                        }
//                    }
//                })
//        }, MoreExecutors.directExecutor())
    }

    private suspend fun loadMediaItem(uri: Uri, track: Track) {
        val imageLoader = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(track.albumImageLink)
            .build()
        val result = imageLoader.execute(request)
        var bytes: ByteArray? = null
        if (result is SuccessResult) {
            val bitmap = result.drawable.toBitmap()
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            bytes = stream.toByteArray()
        }
        val newItem = MediaItem.Builder()
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(track.artistName)
                    .setArtworkData(bytes, MediaMetadata.PICTURE_TYPE_MEDIA)
                    .setTitle(track.title)
                    .build()
            )
            .setMediaId("$uri")
            .build()
        controller.addMediaItem(newItem)
    }

    companion object {
        const val POSITION = "position"
        const val TRACKS = "tracks"

        fun newInstance(param1: String) =
            TrackFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
                }
            }
    }
}