package android.learn.deezer

import android.content.Context
import android.learn.found.presentation.FoundTracksViewModel
import android.learn.list.databinding.FragmentTracksBinding
import android.learn.list.domain.Track
import android.learn.list.presentation.ViewModelFactory
import android.learn.list.presentation.adapter.TracksAdapter
import android.learn.utils.Error
import android.learn.utils.Progress
import android.learn.utils.Result
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoundTracksFragment : Fragment() {
    private var _binding: FragmentTracksBinding? = null
    private val binding: FragmentTracksBinding
        get() = _binding ?: throw RuntimeException("FragmentTracksBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val adapter by lazy {
        TracksAdapter(onGotoTrackListener = object : TracksAdapter.OnGotoTrackListener {
            override fun onGotoTrack() {
                val navController = findNavController()
                navController.navigate(R.id.action_navigation_home_to_navigation_track)
            }
        })
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FoundTracksViewModel::class.java]
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
        setupButtons()

        if (savedInstanceState == null) {
            viewModel.loadChart(limit = 30)
        }

    }

    private fun setupButtons() {
        binding.tryLoadButton.setOnClickListener {
            when (val text = binding.editQuery.text.toString().trim()) {
                "" -> viewModel.loadChart(limit = 30)
                else -> viewModel.loadTrack(text, limit = 30)
            }
        }

        binding.findButton.setOnClickListener {
            when (val text = binding.editQuery.text.toString().trim()) {
                "" -> viewModel.loadChart(limit = 30)
                else -> viewModel.loadTrack(text, limit = 30)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.tracksRv.adapter = adapter
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tracksState.collectLatest {
                    when (it) {
                        is Error -> {
                            binding.errorText.visibility = View.VISIBLE
                            binding.tryLoadButton.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                        }

                        is Progress -> {
                            binding.errorText.visibility = View.GONE
                            binding.tryLoadButton.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result<*> -> {
                            binding.errorText.visibility = View.GONE
                            binding.tryLoadButton.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                            adapter.submitList(
                                (it as Result<List<Track>>).result
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() =
            FoundTracksFragment().apply {
            }
    }
}