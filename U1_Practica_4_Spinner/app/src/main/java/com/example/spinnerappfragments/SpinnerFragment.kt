package com.example.spinnerappfragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_spinner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupSpinner()
        setupButtons()
    }

    override fun onResume() {
        super.onResume()
        loadLastSelectedOption()
    }

    private fun initializeViews(view: View) {
        spinner = view.findViewById(R.id.image_spinner)
        nextPageButton = view.findViewById(R.id.print_button)
        luckButton = view.findViewById(R.id.luck_button)
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        titleView = view.findViewById(R.id.title)

        titleView.text = getString(R.string.select_image_msg)
        nextPageButton.text = getString(R.string.select_image)
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.img_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

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

    private fun setupButtons() {
        nextPageButton.setOnClickListener {
            val selectedOption = spinner.selectedItem.toString()
            saveSelectedOption(selectedOption)
            (requireActivity() as MainActivity).navigateToImageViewer(selectedOption)
        }

        luckButton.setOnClickListener {
            (requireActivity() as MainActivity).navigateToImageViewer("lucky")
        }
    }

    private fun saveSelectedOption(selectedOption: String) {
        Log.d(TAG, "Saving selected option: $selectedOption")
        with(sharedPreferences.edit()) {
            putString(LAST_SELECTED_KEY, selectedOption)
            apply()
        }
    }
}