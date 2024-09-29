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

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var nextPageButton: Button
    private lateinit var luckButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var titleView: TextView
    private lateinit var mainContent: View
    private lateinit var fragmentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupSpinner()
        loadLastSelectedOption()
        setupButtons()
    }

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

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.songs,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun loadLastSelectedOption() {
        val lastSelected = sharedPreferences.getString("last_selected", null)
        lastSelected?.let {
            val position = (spinner.adapter as ArrayAdapter<String>).getPosition(it)
            spinner.setSelection(position)
        }
    }

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

    private fun saveSelectedOption(selectedOption: String) {
        sharedPreferences.edit().putString("last_selected", selectedOption).apply()
    }

    private fun navigateToMusicPlayer(selectedIndex: Int) {
        val musicPlayerFragment = MusicPlayerFragment.newInstance(selectedIndex)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, musicPlayerFragment)
            .addToBackStack(null)
            .commit()
        mainContent.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
    }

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