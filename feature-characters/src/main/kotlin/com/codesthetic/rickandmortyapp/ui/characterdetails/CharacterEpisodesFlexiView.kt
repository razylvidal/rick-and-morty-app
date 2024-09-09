package com.codesthetic.rickandmortyapp.ui.characterdetails

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.feature.characters.R
import com.codesthetic.flexi.BaseFlexiView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible

/**
 * Created by razylvidal on July 02, 2024
 */
class CharacterEpisodesFlexiView(
    val episode: Episode,
) : BaseFlexiView() {
    override fun equals(other: Any?): Boolean {
        if (other !is CharacterEpisodesFlexiView) return false
        return episode.id == other.episode.id
    }

    override fun getLayoutRes(): Int = R.layout.episode_item_layout

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
    ): BaseFlexiViewHolder = CharacterEpisodesViewHolder(view, adapter)

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: BaseFlexiViewHolder?,
        position: Int,
        payloads: MutableList<Any>?,
    ) {
        if (holder is CharacterEpisodesViewHolder) {
            holder.characterEpisode.text = formatEpisode(episode.episode.uppercase())
            holder.characterEpisodeTitle.text = episode.name
            holder.characterEpisodeReleaseDate.text = episode.airDate
        }
    }

    private fun formatEpisode(episode: String): String {
        val index = episode.indexOf('e', 0, true)
        return StringBuilder(episode).insert(index, '.').toString()
    }

    inner class CharacterEpisodesViewHolder(
        view: View,
        adapter: FlexibleAdapter<*>,
    ) : BaseFlexiViewHolder(view, adapter) {
        val characterEpisode: TextView = view.findViewById(R.id.tvEpisode)
        val characterEpisodeTitle: TextView = view.findViewById(R.id.tvEpisodeTitle)
        val characterEpisodeReleaseDate: TextView = view.findViewById(R.id.tvEpisodeReleaseDate)
    }

    override fun hashCode(): Int {
        return episode.hashCode()
    }
}
