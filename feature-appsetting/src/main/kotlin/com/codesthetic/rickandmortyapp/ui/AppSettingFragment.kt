package com.codesthetic.rickandmortyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codesthetic.feature.appsetting.databinding.AppsettingFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by razylvidal on February 23, 2024
 */
@AndroidEntryPoint
class AppSettingFragment : Fragment() {
    private var _binding: AppsettingFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = AppsettingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
