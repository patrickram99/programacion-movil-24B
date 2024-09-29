/*
SpinnerFragment:
Fragment with a Spinner that allows the user to select a song or go with a randomly generated option

Autor                   : Patrick Ramirez
Fecha de creacion       : Domingo 21 de septiembre
Fecha de modificacion   : Domingo 28 de septiembre

*/

package com.example.spinnerappfragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * A fragment that displays a spinner for selecting an image and buttons for navigation.
 */
class SpinnerFragment : Fragment() {
    private lateinit var spinner: Spinner
    private lateinit var nextPageButton: Button
    private lateinit var luckButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var titleView: TextView

    companion object {
        private const val TAG = "SpinnerFragment"
        private const val PREFS_NAME = "MyPrefs"
        private const val LAST_SELECTED_KEY = "last_selected"
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_spinner, container, false)
    }

    /**
     * Called immediately after onCreateView() has returned, but before any saved state has been restored in to the view.
     *
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupSpinner()
        setupButtons()
    }

    /**
     * Initializes the views in the fragment.
     *
     * @param view The root view of the fragment.
     */
    private fun initializeViews(view: View) {
        spinner = view.findViewById(R.id.image_spinner)
        nextPageButton = view.findViewById(R.id.print_button)
        luckButton = view.findViewById(R.id.luck_button)
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        titleView = view.findViewById(R.id.title)

        titleView.text = getString(R.string.select_image_msg)
        nextPageButton.text = getString(R.string.select_image)
    }

    /**
     * Sets up the spinner with options and loads the last selected option.
     */
    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.img_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Load the last selected option
        loadLastSelectedOption()

        // Set up listener to save the selected option
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = parent?.getItemAtPosition(position).toString()
                saveSelectedOption(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    /**
     * Loads the last selected option from SharedPreferences and sets the spinner selection accordingly.
     */
    private fun loadLastSelectedOption() {
        val lastSelected = sharedPreferences.getString(LAST_SELECTED_KEY, null)
        Log.d(TAG, "Loading last selected option: $lastSelected")
        lastSelected?.let {
            val position = (spinner.adapter as ArrayAdapter<String>).getPosition(it)
            if (position != -1) {
                spinner.setSelection(position)
                Log.d(TAG, "Set spinner selection to position: $position")
            } else {
                Log.e(TAG, "Position not found for option: $it")
            }
        } ?: run {
            Log.d(TAG, "No last selected option found, using default")
            spinner.setSelection(0) // Set to first item as default
        }
    }

    /**
     * Sets up the click listeners for the navigation buttons.
     */
    private fun setupButtons() {
        nextPageButton.setOnClickListener {
            val selectedOption = spinner.selectedItem.toString()
            (requireActivity() as MainActivity).navigateToImageViewer(selectedOption)
        }

        luckButton.setOnClickListener {
            (requireActivity() as MainActivity).navigateToImageViewer("lucky")
        }
    }

    /**
     * Saves the selected option to SharedPreferences.
     *
     * @param selectedOption The selected option to be saved.
     */
    private fun saveSelectedOption(selectedOption: String) {
        Log.d(TAG, "Saving selected option: $selectedOption")
        with(sharedPreferences.edit()) {
            putString(LAST_SELECTED_KEY, selectedOption)
            apply()
        }
    }
}