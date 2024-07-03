package com.codesthetic.rickandmortyapp.ui.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.feature_characters.R
import com.codesthetic.feature_characters.databinding.CharacterDetailsFragmentBinding
import com.codesthetic.flexi.BaseFlexiView
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import eu.davidea.flexibleadapter.FlexibleAdapter
import javax.inject.Inject

/**
 * Created by razylvidal on July 01, 2024
 */

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(), CharacterDetailsContract.View {
    @Inject
    lateinit var presenter: CharacterDetailsPresenter

    private val binding by lazy {
        CharacterDetailsFragmentBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        FlexibleAdapter<BaseFlexiView>(emptyList())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        presenter.onViewReady(this, getCharacterId())

        binding.tbCharacterDetails.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setupRecyclerView() {
        binding.rvEpisodes.layoutManager = LinearLayoutManager(context)
        binding.rvEpisodes.itemAnimator = DefaultItemAnimator()
        binding.rvEpisodes.adapter = adapter
        binding.rvEpisodes.setHasFixedSize(true)
    }

    override fun showCharacterDetails(
        character: Character,
        locationName: String,
        episodes: List<Episode>,
    ) {
        binding.tvCharacterName.text = character.name
        binding.sivCharacterImage.loadImage(character.image)
        binding.characterDetails.apply {
            tvName.text = character.name
            tvStatus.text = character.status
            tvSpecies.text = character.species
            tvLocation.text = locationName
            tvGender.text = character.gender
            tvType.text = character.type
        }
        if (episodes.isNotEmpty()) {
            adapter.updateDataSet(episodes.map { CharacterEpisodesFlexiView(it) })
        }
    }

    private fun ShapeableImageView.loadImage(imageUrl: String) {
        load(imageUrl) {
            crossfade(true)
            placeholder(R.drawable.image_placeholder)
            error(R.drawable.image_placeholder)
        }
        scaleType = ImageView.ScaleType.CENTER_CROP

        if (imageUrl.isBlank()) {
            strokeWidth = STROKE_WIDTH
        }
    }

    override fun showLoading() {
        // TODO("Not yet implemented")
    }

    override fun hideLoading() {
        // TODO("Not yet implemented")
    }

    private fun getCharacterId(): Int {
        return requireArguments().getInt(CHARACTER_ID_KEY)
    }

    companion object {
        private const val CHARACTER_ID_KEY = "character_id"
        private const val STROKE_WIDTH = 0f

        fun newInstance(id: Int): CharacterDetailsFragment {
            return CharacterDetailsFragment().apply {
                arguments = bundleOf(CHARACTER_ID_KEY to id)
            }
        }
    }
}
