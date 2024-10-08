package com.codesthetic.rickandmortyapp.ui.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.feature.characters.R
import com.codesthetic.feature.characters.databinding.CharacterDetailsFragmentBinding
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.rickandmortyapp.ui.location.LocationDialogFragment
import com.codesthetic.utilsandroid.capitalizeFirstChar
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
        clickHandlers()

        presenter.onViewReady(this, getCharacterId())

        binding.backButton.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setupRecyclerView() {
        binding.rvEpisodes.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvEpisodes.itemAnimator = DefaultItemAnimator()
        binding.rvEpisodes.adapter = adapter
        binding.rvEpisodes.setHasFixedSize(true)
    }

    private fun clickHandlers() {
        binding.characterDetails.tvLocation.setOnClickListener {
            presenter.onLocationClicked()
        }

        binding.characterDetails.tvOrigin.setOnClickListener {
            presenter.onOriginClicked()
        }
    }

    override fun showCharacterDetails(
        character: Character,
        originName: String,
        locationName: String,
        episodes: List<Episode>,
    ) {
        binding.tvCharacterName.text = character.name
        binding.sivCharacterImage.loadImage(character.image)
        binding.characterDetails.apply {
            tvName.text = character.name
            tvStatus.text = character.status.capitalizeFirstChar()
            tvSpecies.text = character.species
            tvOrigin.text = originName.capitalizeFirstChar()
            tvLocation.text = locationName.capitalizeFirstChar()
            tvGender.text = character.gender.capitalizeFirstChar()
            tvType.text = character.type
        }
        if (character.origin == 0) {
            binding.characterDetails.tvOrigin.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
        if (character.location == 0) {
            binding.characterDetails.tvLocation.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
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

    override fun showLocationBottomSheetDialog(id: Int) {
        LocationDialogFragment.newInstance(id)
            .onCharacterClicked { id ->
                presenter.onUpdateCharacter(id)
            }
            .show(childFragmentManager, DIALOG_KEY)
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun getCharacterId(): Int {
        return requireArguments().getInt(CHARACTER_ID_KEY)
    }

    companion object {
        private const val DIALOG_KEY = "dialog_key"
        private const val CHARACTER_ID_KEY = "character_id"
        private const val STROKE_WIDTH = 0f

        fun newInstance(id: Int): CharacterDetailsFragment {
            return CharacterDetailsFragment().apply {
                arguments = bundleOf(CHARACTER_ID_KEY to id)
            }
        }
    }
}
