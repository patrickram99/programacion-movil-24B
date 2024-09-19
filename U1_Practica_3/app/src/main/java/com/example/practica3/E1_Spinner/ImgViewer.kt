package com.example.practica3.E1_Spinner

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practica3.R

class ImgViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_viewer)

        val imgOption = intent.getStringExtra("option") ?: ""
        val imageView = findViewById<ImageView>(R.id.image_view)
        val titleView = findViewById<TextView>(R.id.title)
        val goBackButton = findViewById<Button>(R.id.go_back_button)

        // Get both string arrays from resources
        val imgOptions = resources.getStringArray(R.array.img_options)
        val imgDrawables = resources.getStringArray(R.array.img_drawables)

        // Create the map dynamically
        val imageMap = imgOptions.zip(imgDrawables).toMap()

        // Set the image
        imageMap[imgOption]?.let { drawableName ->
            val resourceId = resources.getIdentifier(drawableName, "drawable", packageName)
            imageView.setImageResource(resourceId)
        }

        // Set the title
        titleView.text = imgOption

        goBackButton.setOnClickListener {
            finish()
        }
    }
}