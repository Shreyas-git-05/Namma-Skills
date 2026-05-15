package com.nammaskill.app.ui.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nammaskill.app.data.FakeData
import com.nammaskill.app.databinding.FragmentStoriesBinding

class StoriesFragment : Fragment() {

    private var _binding: FragmentStoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StoriesAdapter()
        binding.storiesRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.storiesRecycler.adapter = adapter
        adapter.submitList(FakeData.stories)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

