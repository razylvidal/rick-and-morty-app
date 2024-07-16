package com.codesthetic.rickandmortyapp.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.codesthetic.engine.AppNavigator
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayDataSource.SortByName
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.feature_characters.R
import com.codesthetic.feature_characters.databinding.CharacterFragmentBinding
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.flexi.ProgressItem
import com.codesthetic.flexi.SimpleEndlessScroll
import com.codesthetic.flexi.ThrottledFlexiItemClickedListener
import com.codesthetic.rickandmortyapp.ui.filter.CharactersFilterDialogFragment
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

    @Inject
    lateinit var navigator: AppNavigator

    private val binding by lazy {
        CharacterFragmentBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        FlexibleAdapter<BaseFlexiView>(emptyList())
    }

    private val progressItem = ProgressItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setEndlessScrollThreshold(1)
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

        setupRecycleView()
        clickHandlers()
        setupSearchView()

        presenter.onViewReady(this)
    }

    private fun setupRecycleView() {
        binding.rvCharacters.layoutManager = GridLayoutManager(context, 2)
        binding.rvCharacters.itemAnimator = DefaultItemAnimator()
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.setHasFixedSize(true)
    }

    private fun clickHandlers() {
        binding.characterAppbar.btnFilter.setOnClickListener {
            presenter.onFilterButtonClicked()
        }

        binding.characterAppbar.clearSearchField.setOnClickListener {
            binding.characterAppbar.searchField.setText("")
        }

        binding.btnSort.setOnClickListener {
            presenter.onSortButtonClicked()
        }
    }

    private fun setupSearchView() {
        val searchField = binding.characterAppbar.searchField
        searchField.setTextColor(Color.DKGRAY)
        searchField.setHintTextColor(Color.GRAY)
        binding.characterAppbar.clearSearchField.imageTintMode = PorterDuff.Mode.SRC_IN
        searchField.doAfterTextChanged { text ->
            Log.e("doAfterTextChanged", text.toString())
            if (text.toString().isNotEmpty()) {
                binding.characterAppbar.clearSearchField.visibility = View.VISIBLE
            } else {
                binding.characterAppbar.clearSearchField.visibility = View.GONE
            }
            presenter.onSearchValueChanged(text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun renderLoading(isVisible: Boolean) {
        if (isVisible) {
            binding.rvCharacters.visibility = View.GONE
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.rvCharacters.visibility = View.VISIBLE
            binding.loading.visibility = View.GONE
        }
    }

    override fun showFilter(
        gender: List<String>,
        status: List<String>,
    ) {
        CharactersFilterDialogFragment()
            .setStatus(status)
            .setGender(gender)
            .onApply { filter ->
                presenter.onApplyFilters(filter)
            }
            .onReset {
                presenter.onResetFilters()
            }
            .show(childFragmentManager, "filter_bottom_sheet_dialog")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showCharacters(
        characters: List<Character>,
        noMoreToLoad: Boolean,
        isRestoringDefaults: Boolean,
    ) {
        val newSetOfCharacters = characters.map { CharacterFlexiView(it) }
        val currentDisplayedCharacters = getCurrentDisplayCharacters()

        if (newSetOfCharacters != currentDisplayedCharacters) {
            if (isRestoringDefaults) {
                adapter.clear()
                adapter.notifyDataSetChanged()
                adapter.updateDataSet(newSetOfCharacters)
            } else {
                val diff = newSetOfCharacters - currentDisplayedCharacters.toSet()
                adapter.onLoadMoreComplete(diff)
            }
        }

        if (noMoreToLoad) {
            disableEndlessScrolling()
        } else {
            setUpEndlessScrolling()
        }
    }

    override fun showFilteredCharacters(characters: List<Character>) {
        adapter.updateDataSet(
            characters.map { CharacterFlexiView(it) }
        )
        disableEndlessScrolling()
    }

    private fun getCurrentDisplayCharacters(): List<CharacterFlexiView> {
        return adapter.currentItems.filterIsInstance<CharacterFlexiView>()
    }

    private fun setUpEndlessScrolling() {
        if (!adapter.isEndlessScrollEnabled) {
            adapter.removeAllScrollableFooters()
            val listener =
                object : SimpleEndlessScroll {
                    override fun onLoadMore(
                        lastPosition: Int,
                        currentPage: Int,
                    ) {
                        presenter.onLoadMore(lastPosition)
                    }
                }
            adapter.setEndlessScrollListener(listener, progressItem)
        }
    }

    private fun disableEndlessScrolling() {
        adapter.onLoadMoreComplete(null)
        adapter.setEndlessProgressItem(null)
    }

    override fun renderEmptyState(
        message: String,
        isVisible: Boolean,
    ) {
        if (isVisible) {
            if (message.isNotEmpty()) {
                binding.tvEmtyStateMessage.text = message
            }
            binding.rvCharacters.visibility = View.GONE
            binding.llEmptyState.visibility = View.VISIBLE
        } else {
            binding.rvCharacters.visibility = View.VISIBLE
            binding.llEmptyState.visibility = View.GONE
        }
    }

    override fun navigateToCharacter(id: Int) {
        navigator.navigateToCharacterDetails(requireActivity(), id)
    }

    override fun updateSortByNameButton(selected: SortByName) {
        when (selected) {
            SortByName.DEFAULT -> {
                binding.btnSort.setImageResource(R.drawable.baseline_sort_by_alpha_24)
            }

            SortByName.ASCENDING -> {
                binding.btnSort.setImageResource(R.drawable.baseline_arrow_downward_24)
            }

            SortByName.DESCENDING -> {
                binding.btnSort.setImageResource(R.drawable.baseline_arrow_upward_24)
            }
        }
    }
}
