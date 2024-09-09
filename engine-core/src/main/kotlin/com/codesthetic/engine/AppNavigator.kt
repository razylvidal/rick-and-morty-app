package com.codesthetic.engine

import android.app.Activity

/**
 * Created by razylvidal on March 18, 2024
 */
interface AppNavigator {
    fun navigateToMain(activity: Activity)

    fun navigateToCharacterDetails(
        activity: Activity,
        id: Int,
    )
}
