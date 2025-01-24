package com.codesthetic.rickandmortyapp.ui.episodedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.feature.episodes.databinding.EpisodeDetailsBinding
import javax.inject.Inject

/**
 * Created by razylvidal on 1/24/25.
 */
class EpisodeDetailsFragment : Fragment(), EpisodeDetailsContracts.View {

    private var  _binding: EpisodeDetailsBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var presenter : EpisodeDetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = EpisodeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewReady(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showEpisodeDetails(episode: Episode) {
//        TODO("Not yet implemented")
    }

    override fun showEpisodeCharacters(characters: List<Character>) {
//        TODO("Not yet implemented")
    }
}
