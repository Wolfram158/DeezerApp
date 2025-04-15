package android.learn.found.presentation

import android.learn.found.databinding.FragmentFoundTracksBinding
import android.learn.list.presentation.adapter.TracksAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FoundTracksFragment : Fragment() {
    private val binding by lazy {
        FragmentFoundTracksBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        TracksAdapter()
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

    }

    private fun setupRecyclerView() {
        binding.tracksRv.adapter = adapter
    }

    companion object {
        fun newInstance() =
            FoundTracksFragment().apply {
            }
    }
}