package com.codesthetic.rickandmortyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.feature_characters.databinding.CharacterFragmentBinding
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.flexi.ThrottledFlexiItemClickedListener
import com.codesthetic.rickandmortyapp.ui.flexiitems.CharacterFlexiView
import dagger.hilt.android.AndroidEntryPoint
import eu.davidea.flexibleadapter.FlexibleAdapter
import timber.log.Timber
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

    private val adapter by lazy {
        FlexibleAdapter<BaseFlexiView>(emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.mItemClickListener =
            object : ThrottledFlexiItemClickedListener() {
                override fun onSingleClicked(
                    view: View?,
                    position: Int,
                ) {
                    val character = adapter.getItem(position) as? CharacterFlexiView
                    if (character != null) {
                        presenter.onCharacterClicked(character.character.id)
                    } else {
                        Timber.e("Item cannot be cast to CharacterFlexiView")
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

        presenter.onViewReady(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoading() {
        // TODO("Not yet implemented")
    }

    override fun hideLoading() {
        //TODO("Not yet implemented")


    }

    override fun showFilter() {
       // TODO("Not yet implemented")
    }

    override fun hideFilter() {
       // TODO("Not yet implemented")
    }

    override fun showCharacters(characters : List<Character>) {
        // TODO("Not yet implemented")
    }

    override fun showEmptyState() {
       // TODO("Not yet implemented")
    }

    override fun navigateToCharacter() {
      //  TODO("Not yet implemented")
    }
}
