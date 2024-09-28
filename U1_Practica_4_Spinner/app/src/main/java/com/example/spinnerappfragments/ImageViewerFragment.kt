package com.example.spinnerappfragments

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.net.HttpURLConnection
import java.net.URL


class ImageViewerFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var goBackButton: Button

    companion object {
        private const val ARG_OPTION = "option"

        fun newInstance(option: String): ImageViewerFragment {
            val fragment = ImageViewerFragment()
            val args = Bundle()
            args.putString(ARG_OPTION, option)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        displayImage()
        setupGoBackButton()
    }

    private fun initializeViews(view: View) {
        imageView = view.findViewById(R.id.image_view)
        titleView = view.findViewById(R.id.title)
        goBackButton = view.findViewById(R.id.go_back_button)
    }

    private fun displayImage() {
        val option = arguments?.getString(ARG_OPTION) ?: ""

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
            val resourceId = resources.getIdentifier(drawableName, "drawable", requireContext().packageName)
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

    private fun setupGoBackButton() {
        goBackButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
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
    }}