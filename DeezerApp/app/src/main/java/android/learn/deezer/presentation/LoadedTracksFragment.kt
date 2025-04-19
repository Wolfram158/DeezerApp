package android.learn.deezer.presentation

import android.content.Context
import android.learn.deezer.R
import android.learn.deezer.presentation.TrackFragment.Companion.POSITION
import android.learn.deezer.presentation.TrackFragment.Companion.TRACKS
import android.learn.list.databinding.FragmentTracksBinding
import android.learn.list.domain.Track
import android.learn.list.presentation.ViewModelFactory
import android.learn.list.presentation.adapter.TracksAdapter
import android.learn.loaded.presentation.LoadedTracksViewModel
import android.learn.track.presentation.TrackViewModel
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadedTracksFragment : Fragment() {
    private var _binding: FragmentTracksBinding? = null
    private val binding: FragmentTracksBinding
        get() = _binding ?: throw RuntimeException("FragmentTracksBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val adapter by lazy {
        TracksAdapter(onGotoTrackListener = object : TracksAdapter.OnGotoTrackListener {
            override fun onGotoTrack(position: Int, tracks: List<Track>) {
                val navController = findNavController()
                val bundle = Bundle().apply {
                    putParcelableArray(TRACKS, tracks.toTypedArray())
                    putInt(POSITION, position)
                }
                navController.navigate(R.id.action_navigation_loaded_to_navigation_track, bundle)
            }
        })
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoadedTracksViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launch {
            adapter.submitList(viewModel.loadTracks())
        }
    }

    private fun setupRecyclerView() {
        binding.tracksRv.adapter = adapter
    }
}