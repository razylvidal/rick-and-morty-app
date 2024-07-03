package com.codesthetic.rickandmortyapp.ui.characterdetails

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codesthetic.feature_characters.R
import com.codesthetic.feature_characters.databinding.CharacterDetailsActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by razylvidal on July 02, 2024
 */

@AndroidEntryPoint
class CharacterDetailsActivity : AppCompatActivity() {
    private val binding by lazy {
        CharacterDetailsActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_details_activity)

        if (hasIntentExtras() && savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    CharacterDetailsFragment.newInstance(intent!!.extras!!.getInt(KEY_CHARACTER_ID)),
                    CharacterDetailsFragment::class.java.name
                )
                .commit()
        }
    }

    private fun Activity.hasIntentExtras(): Boolean {
        return intent != null && intent.extras != null
    }

    companion object {
        const val KEY_CHARACTER_ID = "character_id"
    }
}
