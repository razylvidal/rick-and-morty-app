package com.codesthetic.rickandmortyapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.feature.episodes.databinding.EpisodesFragmentBinding
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.flexi.ThrottledFlexiItemClickedListener
import com.codesthetic.rickandmortyapp.ui.season.SeasonDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import eu.davidea.flexibleadapter.FlexibleAdapter
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */

@AndroidEntryPoint
class EpisodesFragment : Fragment(), EpisodesContracts.View {
    @Inject
    lateinit var presenter: EpisodesPresenter

    private val binding by lazy {
        EpisodesFragmentBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        FlexibleAdapter<BaseFlexiView>(emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setEndlessScrollThreshold(1)
        adapter.mItemClickListener =
            object : ThrottledFlexiItemClickedListener() {
                override fun onSingleClicked(
                    view: View?,
                    position: Int,
                ) {
                    val episode = adapter.getItem(position) as? EpisodeFlexiView
                    if (episode != null) {
                        presenter.onEpisodeClicked(episode.episode.id)
                    } else {
                        Timber.e("Item cannot be cast to EpisodeFlexiView")
                    }
                }
            }
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
        presenter.onViewReady(this)
    }

    private fun setupRecyclerView() {
        binding.rvEpisode.layoutManager = LinearLayoutManager(context)
        binding.rvEpisode.itemAnimator = DefaultItemAnimator()
        binding.rvEpisode.adapter = adapter
        binding.rvEpisode.setHasFixedSize(true)
    }

    private fun clickHandlers() {
        binding.tvSeason.setOnClickListener {
            presenter.onSeasonClicked()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showEpisodes(episodes: List<Episode>) {
        adapter.clear()
        adapter.updateDataSet(
            episodes.map { EpisodeFlexiView(it) }
        )
        adapter.notifyDataSetChanged()
    }

    override fun renderSeason(season: String) {
        binding.tvSeason.text = season
    }

    override fun showSeasonDialog(
        currentSeason: Int,
        seasons: List<Int>,
    ) {
        SeasonDialogFragment.newInstance(currentSeason, seasons)
            .show(childFragmentManager, "dialog")
    }
}
