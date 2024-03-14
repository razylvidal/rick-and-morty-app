package com.codesthetic.rickandmortyapp.ui.flexiitems

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.feature_characters.R
import com.codesthetic.flexi.BaseFlexiView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible

/**
 * Created by razylvidal on March 14, 2024
 */
class CharacterFlexiView(
    val character: Character,
) : BaseFlexiView() {
    override fun equals(other: Any?): Boolean {
        if (other !is CharacterFlexiView) return false
        return (character.id == other.character.id) && character.name.equals(other.character.name)
    }

    override fun getLayoutRes(): Int = R.layout.character_frame_layout

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
    ): BaseFlexiViewHolder {
        return CharacterViewHolder(view, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: BaseFlexiViewHolder?,
        position: Int,
        payloads: MutableList<Any>?,
    ) {
        if (holder is CharacterViewHolder) {
//            holder.characterImage.loadImage(character.image)
            holder.characterName.text = character.name
            holder.characterStatus.text = character.status
        }
    }

    override fun hashCode(): Int {
        return character.hashCode()
    }

    inner class CharacterViewHolder(
        view: View,
        adapter: FlexibleAdapter<*>,
    ) : BaseFlexiViewHolder(view, adapter) {
        //        val characterImage: ImageView = view.findViewById(R.id.ivCharacter)
        val characterName: TextView = view.findViewById(R.id.tvCharacterName)
        val characterStatus: TextView = view.findViewById(R.id.tvCharacterStatus)
    }

//    private fun ImageView.loadImage(imageUrl : String) {
//
//    }
}
