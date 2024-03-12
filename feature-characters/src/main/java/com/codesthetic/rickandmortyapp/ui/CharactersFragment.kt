package com.codesthetic.rickandmortyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codesthetic.feature_characters.databinding.CharacterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by razylvidal on September 10, 2023
 */

@AndroidEntryPoint
class CharactersFragment : Fragment(), CharactersContract.View {

    @Inject
    lateinit var presenter: CharactersPresenter

    private val binding by lazy {
        CharacterFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =  binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.onViewReady(this)

        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun setupRecyclerView() {

    }
}
