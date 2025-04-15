package android.learn.found.presentation

import android.content.Context
import android.learn.found.databinding.FragmentFoundTracksBinding
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoundTracksFragment : Fragment() {
    private val binding by lazy {
        FragmentFoundTracksBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val adapter by lazy {
        TracksAdapter(onGotoTrackListener = object: TracksAdapter.OnGotoTrackListener {
            override fun onGotoTrack() {
                TODO("Not yet implemented")
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        binding.tracksRv.adapter = adapter
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.tracksState.collectLatest {
                when (it) {
                    is Error -> TODO()
                    is Progress -> TODO()
                    is Result<*> -> TODO()
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