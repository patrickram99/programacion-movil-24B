package com.example.practica3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var nextPageButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.image_spinner)
        nextPageButton = findViewById(R.id.print_button)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        ArrayAdapter.createFromResource(
            this,
            R.array.img_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Load the last selected option
        val lastSelected = sharedPreferences.getString("last_selected", null)
        lastSelected?.let {
            val position = (spinner.adapter as ArrayAdapter<String>).getPosition(it)
            spinner.setSelection(position)
        }

        nextPageButton.setOnClickListener {
            val selectedOption = spinner.selectedItem.toString()

            // Save the selected option
            sharedPreferences.edit().putString("last_selected", selectedOption).apply()

            val intent = Intent(this@MainActivity, ImgViewer::class.java)
            intent.putExtra("option", selectedOption)
            startActivity(intent)
        }
    }
}