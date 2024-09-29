package com.example.musicappfragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

/**
 * The main activity of the music app.
 *
 * This activity allows the user to select a song from a spinner and navigate to the music player.
 * It also provides a button to randomly select a song.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var nextPageButton: Button
    private lateinit var luckButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var titleView: TextView
    private lateinit var mainContent: View
    private lateinit var fragmentContainer: View

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied in
     * [onSaveInstanceState]. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupSpinner()
        loadLastSelectedOption()
        setupButtons()
    }

    /**
     * Initializes the views used in the activity.
     */
    private fun initializeViews() {
        spinner = findViewById(R.id.song_spinner)
        nextPageButton = findViewById(R.id.select_song_button)
        luckButton = findViewById(R.id.random_song_button)
        titleView = findViewById(R.id.main_title)
        mainContent = findViewById(R.id.main_content)
        fragmentContainer = findViewById(R.id.fragment_container)

        titleView.text = getString(R.string.select_song_msg)
        nextPageButton.text = getString(R.string.select_song)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    }

    /**
     * Sets up the spinner with the list of songs.
     */
    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.songs,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    /**
     * Loads the last selected song from shared preferences and sets it as the selected item in the spinner.
     */
    private fun loadLastSelectedOption() {
        val lastSelected = sharedPreferences.getString("last_selected", null)
        lastSelected?.let {
            val position = (spinner.adapter as ArrayAdapter<String>).getPosition(it)
            spinner.setSelection(position)
        }
    }

    /**
     * Sets up the click listeners for the buttons.
     */
    private fun setupButtons() {
        nextPageButton.setOnClickListener {
            val selectedOption = spinner.selectedItem.toString()
            val selectedIndex = spinner.selectedItemPosition
            saveSelectedOption(selectedOption)
            navigateToMusicPlayer(selectedIndex)
        }
        luckButton.setOnClickListener {
            navigateToMusicPlayer(Random.nextInt(0, spinner.count))
        }
    }

    /**
     * Saves the selected song option to shared preferences.
     *
     * @param selectedOption The selected song option.
     */
    private fun saveSelectedOption(selectedOption: String) {
        sharedPreferences.edit().putString("last_selected", selectedOption).apply()
    }

    /**
     * Navigates to the music player fragment with the selected song index.
     *
     * @param selectedIndex The index of the selected song.
     */
    private fun navigateToMusicPlayer(selectedIndex: Int) {
        val musicPlayerFragment = MusicPlayerFragment.newInstance(selectedIndex)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, musicPlayerFragment)
            .addToBackStack(null)
            .commit()
        mainContent.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            mainContent.visibility = View.VISIBLE
            fragmentContainer.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }
}