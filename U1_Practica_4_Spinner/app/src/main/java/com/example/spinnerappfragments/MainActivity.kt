package com.example.spinnerappfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * The main activity of the application.
 *
 * This activity handles the navigation between fragments and sets up the initial fragment.
 */
class MainActivity : AppCompatActivity() {
    /**
     * Called when the activity is starting.
     *
     * This method sets the content view and initializes the first fragment if savedInstanceState is null.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SpinnerFragment())
                .commit()
        }
    }

    /**
     * Navigates to the ImageViewerFragment with the selected option.
     *
     * This method creates a new instance of the ImageViewerFragment with the provided option,
     * replaces the current fragment in the fragment container, and adds the transaction to the back stack.
     *
     * @param option The selected option to be passed to the ImageViewerFragment.
     */
    fun navigateToImageViewer(option: String) {
        val fragment = ImageViewerFragment.newInstance(option)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}