/*
ImgViewer:
Class that shows the image selected by the user, it allows to go back to the selection section
without losing the alternative

Autor                   : Patrick Ramirez
Fecha de creacion       : Viernes 15 de septiembre
Fecha de modificacion   : Domingo 22 de septiembre

*/

package com.example.practica3.E1_Spinner

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practica3.R
import java.net.HttpURLConnection
import java.net.URL

/**
 * Activity for displaying images based on user selection or random selection.
 */
class ImgViewer : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var goBackButton: Button

    /**
     * Initializes the activity, sets up views, and displays the selected image.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_viewer)

        initializeViews()
        displayImage()
        setupGoBackButton()
    }

    /**
     * Initializes view references.
     */
    private fun initializeViews() {
        imageView = findViewById(R.id.image_view)
        titleView = findViewById(R.id.title)
        goBackButton = findViewById(R.id.go_back_button)
    }

    /**
     * Displays the image based on the selected option.
     */
    private fun displayImage() {
        val option = intent.getStringExtra("option") ?: ""

        if (option == "lucky") {
            displayLuckyImage()
        } else {
            displaySelectedImage(option)
        }
    }

    /**
     * Displays the selected image from local resources.
     *
     * @param option The selected image option.
     */
    private fun displaySelectedImage(option: String) {
        val imgOptions = resources.getStringArray(R.array.img_options)
        val imgDrawables = resources.getStringArray(R.array.img_drawables)
        val imageMap = imgOptions.zip(imgDrawables).toMap()

        imageMap[option]?.let { drawableName ->
            val resourceId = resources.getIdentifier(drawableName, "drawable", packageName)
            imageView.setImageResource(resourceId)
            titleView.text = option
        } ?: run {
            titleView.text = getString(R.string.image_not_found)
        }
    }

    /**
     * Displays a random image from Lorem Picsum.
     */
    private fun displayLuckyImage() {
        ImageLoadTask().execute()
    }

    /**
     * Sets up the go back button to finish the activity.
     */
    private fun setupGoBackButton() {
        goBackButton.setOnClickListener {
            finish()
        }
    }

    /**
     * AsyncTask to load image from Lorem Picsum in the background.
     */
    private inner class ImageLoadTask : AsyncTask<Void, Void, android.graphics.Bitmap?>() {
        override fun doInBackground(vararg params: Void): android.graphics.Bitmap? {
            try {
                val imageUrl = getLoremPicsumImageUrl()
                return BitmapFactory.decodeStream(URL(imageUrl).openConnection().getInputStream())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: android.graphics.Bitmap?) {
            if (result != null) {
                imageView.setImageBitmap(result)
                titleView.text = getString(R.string.random_image)
            } else {
                titleView.text = getString(R.string.error_loading_image)
            }
        }
    }

    /**
     * Retrieves the image URL from Lorem Picsum.
     *
     * @return The URL of the random image.
     */
    private fun getLoremPicsumImageUrl(): String {
        val url = URL("https://picsum.photos/200/300")
        val connection = url.openConnection() as HttpURLConnection
        connection.instanceFollowRedirects = false
        connection.connect()

        val locationHeader = connection.getHeaderField("Location")
        connection.disconnect()

        return locationHeader ?: throw Exception("Failed to get image URL")
    }
}