package android.learn.deezer.presentation

import android.content.Context
import android.learn.deezer.databinding.FragmentTrackBinding
import android.learn.list.domain.Track
import android.learn.list.presentation.ViewModelFactory
import android.learn.track.presentation.TrackViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.launch
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

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controllerFuture.addListener({
            _controller = controllerFuture.get()
            controller.mediaMetadata.let {
                binding.albumNameView.text = it.albumTitle
                binding.titleView.text = it.title
                binding.artistView.text = it.artist
            }
            controller.addListener(object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    mediaItem?.let {
                        binding.albumNameView.text = it.mediaMetadata.albumTitle
                        binding.titleView.text = it.mediaMetadata.title
                        binding.artistView.text = it.mediaMetadata.artist
                    }
                }
            })
            lifecycleScope.launch {
                viewModel.loadMediaItem(
                    tracks[position]
                ) { controller.addMediaItem(it) }
                controller.prepare()
                controller.play()
                binding.playerView.player = controller
                binding.playerView.showController()
            }
        }, MoreExecutors.directExecutor())

    }

    companion object {
        const val POSITION = "position"
        const val TRACKS = "tracks"

    }
}