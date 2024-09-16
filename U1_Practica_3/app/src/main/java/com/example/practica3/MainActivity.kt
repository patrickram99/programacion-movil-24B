package com.example.practica3

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.ComponentActivity

/**
 * The main activity of the application.
 */
class MainActivity : ComponentActivity() {

    private lateinit var spinner: Spinner
    private lateinit var spinnerHandler: SpinnerHandler
    private lateinit var nextPageButton: Button

    /**
     * Called when the activity is starting.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load elements into the View
        spinner = findViewById(R.id.image_spinner)
        nextPageButton = findViewById(R.id.print_button)

        spinnerHandler = SpinnerHandler()

        // Set up the Spinner with an ArrayAdapter from R
        ArrayAdapter.createFromResource(
            this,
            R.array.img_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Link the spinner value to variable
        spinner.onItemSelectedListener = spinnerHandler


        nextPageButton.setOnClickListener{
            val intent = Intent(this@MainActivity, ImgViewer::class.java)
            intent.putExtra("option", spinner.selectedItem.toString())
            startActivity(intent)
        }
    }
}