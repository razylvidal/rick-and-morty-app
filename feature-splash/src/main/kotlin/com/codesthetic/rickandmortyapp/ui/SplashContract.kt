package com.codesthetic.rickandmortyapp.ui

/**
 * Created by razylvidal on March 15, 2024
 */
interface SplashContract {
    interface View {
        fun showLoadingBar()

        fun hideLoadingBar()

        fun showFailedDialog(
            title: String,
            message: String,
        )

        fun navigateToMainActivity()

        fun updateProgressIndicator(progress: Int)

        fun showToast(message: String)
    }

    interface Presenter {
        fun onViewReady(view: View)

        fun onDestroy()
    }
}
