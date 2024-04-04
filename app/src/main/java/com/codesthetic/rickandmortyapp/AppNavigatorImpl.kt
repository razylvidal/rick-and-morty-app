package com.codesthetic.rickandmortyapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.codesthetic.engine.AppNavigator
import javax.inject.Inject

/**
 * Created by razylvidal on March 18, 2024
 */
class AppNavigatorImpl @Inject constructor(
    private val context: Context
) : AppNavigator {
// test
    override fun navigateToMain(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
        activity.finishAffinity()
    }

}
