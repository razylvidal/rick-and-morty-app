package com.codesthetic.rickandmortyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codesthetic.feature_episodes.databinding.EpisodesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by razylvidal on February 05, 2024
 */

@AndroidEntryPoint
class EpisodesFragment : Fragment() {
    private lateinit var binding: EpisodesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = EpisodesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
