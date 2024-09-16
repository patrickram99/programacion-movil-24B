package com.example.practica3

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ImgViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_viewer)

        val imgOption = intent.getStringExtra("option") ?: ""
        val imageView = findViewById<ImageView>(R.id.image_view)
        val titleView = findViewById<TextView>(R.id.title)

        val imageMap = mapOf(
            "Gato" to R.drawable.cat,
            "Perro" to R.drawable.dog,
            "Conejo" to R.drawable.bunny,
            "Huron" to R.drawable.huron,
        )

        // Set the image
        imageMap[imgOption]?.let { imageResource ->
            imageView.setImageResource(imageResource)
        }

        // Set the title
        titleView.text = (imgOption ?: R.string.no_image).toString()
    }
}