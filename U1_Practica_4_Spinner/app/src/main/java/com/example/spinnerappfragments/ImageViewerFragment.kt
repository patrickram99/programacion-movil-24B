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

/**
 * A fragment that displays an image based on the selected option.
 *
 * This fragment allows the user to view an image either from local resources or a random image from Lorem Picsum.
 * It also provides a "Go Back" button to navigate back to the previous screen.
 */
class ImageViewerFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var goBackButton: Button

    companion object {
        private const val ARG_OPTION = "option"

        /**
         * Creates a new instance of the [ImageViewerFragment] with the specified option.
         *
         * @param option The selected image option.
         * @return A new instance of the [ImageViewerFragment].
         */
        fun newInstance(option: String): ImageViewerFragment {
            val fragment = ImageViewerFragment()
            val args = Bundle()
            args.putString(ARG_OPTION, option)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_viewer, container, false)
    }

    /**
     * Called immediately after [onCreateView] has returned, but before any saved state has been restored in to the view.
     *
     * @param view The View returned by [onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        displayImage()
        setupGoBackButton()
    }

    /**
     * Initializes the views used in the fragment.
     *
     * @param view The root view of the fragment.
     */
    private fun initializeViews(view: View) {
        imageView = view.findViewById(R.id.image_view)
        titleView = view.findViewById(R.id.title)
        goBackButton = view.findViewById(R.id.go_back_button)
    }

    /**
     * Displays the image based on the selected option.
     *
     * If the option is "lucky", a random image from Lorem Picsum is displayed.
     * Otherwise, the corresponding image from local resources is displayed.
     */
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

    /**
     * Sets up the "Go Back" button click listener to navigate back to the previous screen.
     */
    private fun setupGoBackButton() {
        goBackButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    /**
     * AsyncTask to load image from Lorem Picsum in the background.
     */
    private inner class ImageLoadTask : AsyncTask<Void, Void, android.graphics.Bitmap?>() {
        /**
         * Performs the background operation to load the image from Lorem Picsum.
         *
         * @param params The parameters of the task, which are not used in this case.
         * @return The loaded bitmap image, or null if an error occurred.
         */
        override fun doInBackground(vararg params: Void): android.graphics.Bitmap? {
            try {
                val imageUrl = getLoremPicsumImageUrl()
                return BitmapFactory.decodeStream(URL(imageUrl).openConnection().getInputStream())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * Runs on the UI thread after the background computation finishes.
         *
         * @param result The result of the background computation.
         */
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
     * @throws Exception if failed to get the image URL.
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