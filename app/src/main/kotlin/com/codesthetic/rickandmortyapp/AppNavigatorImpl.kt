package com.codesthetic.rickandmortyapp

import android.app.Activity
import android.content.Intent
import com.codesthetic.engine.AppNavigator
import com.codesthetic.rickandmortyapp.ui.characterdetails.CharacterDetailsActivity
import com.codesthetic.rickandmortyapp.ui.episodedetails.EpisodeDetailsFragment
import javax.inject.Inject

/**
 * Created by razylvidal on March 18, 2024
 */
class AppNavigatorImpl
    @Inject
    constructor() : AppNavigator {
        override fun navigateToMain(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            activity.finishAffinity()
        }

        override fun navigateToCharacterDetails(
            activity: Activity,
            id: Int,
        ) {
            val intent = Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra(CharacterDetailsActivity.KEY_CHARACTER_ID, id)
            activity.startActivity(intent)
        }

    override fun navigateToEpisodeDetails(activity: Activity, id: Int) {
        val tag = EpisodeDetailsFragment::class.java.name
        val fragmentHelper = activity.
    }




}
