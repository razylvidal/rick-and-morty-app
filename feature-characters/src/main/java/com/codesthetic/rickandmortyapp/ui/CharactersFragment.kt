package com.codesthetic.rickandmortyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codesthetic.feature_characters.databinding.CharacterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by razylvidal on September 10, 2023
 */

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private lateinit var binding: CharacterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = CharacterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
