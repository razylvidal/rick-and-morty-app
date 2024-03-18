package com.codesthetic.rickandmortyapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codesthetic.engine.AppNavigator
import com.codesthetic.feature_splash.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by razylvidal on March 15, 2024
 */
@AndroidEntryPoint
class SplashActivity : AppCompatActivity(), SplashContract.View {
    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var presenter: SplashPresenter

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter.onViewReady(this)
    }

    override fun showLoadingBar() {
        binding.progressIndicator.visibility = View.VISIBLE
    }

    override fun updateProgressIndicator(progress: Int) {
        binding.progressIndicator.progress = progress
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideLoadingBar() {
        binding.progressIndicator.visibility = View.GONE
    }

    override fun showFailedDialog(
        title: String,
        message: String,
    ) {
        // TODO("Not yet implemented")
    }

    override fun navigateToMainActivity() {
        navigator.navigateToMain(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
