package com.example.practica3.E1_Spinner

import android.view.View
import android.widget.AdapterView

/**
 * Handles the selection events for a Spinner.
 */
class SpinnerHandler : AdapterView.OnItemSelectedListener {
    /**
     * The currently selected item in the Spinner.
     *
     * This variable is updated every time a new item is selected.
     */
    var selectedItem: String = ""
        private set

    /**
     * Called when an item in the Spinner is selected.
     *
     * @param parent The AdapterView where the selection happened.
     * @param view The view within the AdapterView that was clicked.
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that is selected.
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        selectedItem = parent.getItemAtPosition(position).toString()
    }

    /**
     * Called when no item in the Spinner is selected.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Do nothing
    }
}