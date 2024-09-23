package com.example.practica3.E2_MusicApp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practica3.R
import kotlin.random.Random

/**
 * Main activity for the Spinner application.
 * Allows users to select an image from a spinner or choose a random image.
 */
class Landing : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var nextPageButton: Button
    private lateinit var luckButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var titleView: TextView

    /**
     * Initializes the activity, sets up views, and loads the last selected option.
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
     * Initializes view references and shared preferences.
     */
    private fun initializeViews() {
        spinner = findViewById(R.id.image_spinner)
        nextPageButton = findViewById(R.id.print_button)
        luckButton = findViewById(R.id.luck_button)
        titleView = findViewById(R.id.title)

        titleView.text = getString(R.string.select_song_msg)
        nextPageButton.text = getString(R.string.select_song)


        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    }

    /**
     * Sets up the spinner with song options.
     */
    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.songs,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.invalidate()

        spinner.post {
            spinner.adapter = adapter
        }
    }

    /**
     * Loads and sets the last selected option from shared preferences.
     */
    private fun loadLastSelectedOption() {
        val lastSelected = sharedPreferences.getString("last_selected", null)
        lastSelected?.let {
            val position = (spinner.adapter as ArrayAdapter<String>).getPosition(it)
            spinner.setSelection(position)
        }
    }

    /**
     * Sets up the "Next" and "Lucky" buttons with their respective click listeners.
     */
    private fun setupButtons() {
        nextPageButton.setOnClickListener {
            val selectedOption = spinner.selectedItem.toString()
            val selectedIndex = spinner.selectedItemPosition
            saveSelectedOption(selectedOption)
            navigateToImageViewer(selectedIndex)
        }
        luckButton.setOnClickListener {
            navigateToImageViewer(Random.nextInt(0, 5))
        }
    }

    /**
     * Saves the selected option to shared preferences.
     *
     * @param selectedOption The option selected by the user.
     */
    private fun saveSelectedOption(selectedOption: String) {
        sharedPreferences.edit().putString("last_selected", selectedOption).apply()
    }

    /**
     * Navigates to the ImgViewer activity with the selected option.
     *
     * @param selectedOption The option to be passed to the ImgViewer activity.
     */
    private fun navigateToImageViewer(selectedIndex: Int) {
        val intent = Intent(this, MusicApp::class.java)
        intent.putExtra("selected_index", selectedIndex)  // Pass the index to the next activity
        startActivity(intent)
    }
}