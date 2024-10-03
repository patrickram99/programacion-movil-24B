/*
SongListFragment:
Fragment that contains the RecyclerView with the list of songs.

Autor                   : Patrick Ramirez
Fecha de creacion       : Martes 01 de octubre
Fecha de modificacion   : Jueves 03 de octubre

*/

package com.example.u1_practica_5_recyclerview.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.u1_practica_5_recyclerview.MusicPlayerCallback
import com.example.u1_practica_5_recyclerview.R
import com.example.u1_practica_5_recyclerview.Song
import com.example.u1_practica_5_recyclerview.SongProvider
import com.example.u1_practica_5_recyclerview.adapter.SongsAdapter

/**
 * A fragment representing a list of Songs.
 */
class SongListFragment : Fragment() {
    private lateinit var songAdapter: SongsAdapter
    private lateinit var userRecycler: RecyclerView
    private var musicPlayerCallback: MusicPlayerCallback? = null

    /**
     * Called when a fragment is first attached to its context.
     * @param context The context to which the fragment is attached.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        musicPlayerCallback = context as? MusicPlayerCallback
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_song_list, container, false)
        userRecycler = view.findViewById(R.id.user_list)
        return view
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view.
     * @param view The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    /**
     * Initializes the RecyclerView with a LinearLayoutManager, DividerItemDecoration, and SongsAdapter.
     */
    private fun initRecyclerView() {
        val manager = LinearLayoutManager(requireContext())
        val decorator = DividerItemDecoration(requireContext(), manager.orientation)

        // Get the SongProvider instance using the application context
        val songProvider = SongProvider.getInstance(requireContext().applicationContext)

        songAdapter = SongsAdapter(songProvider.listaSongs, ::onItemSelected)
        userRecycler.layoutManager = manager
        userRecycler.adapter = songAdapter

        userRecycler.addItemDecoration(decorator)
    }

    /**
     * Handles the selection of a song item.
     * @param song The selected Song object.
     */
    private fun onItemSelected(song: Song) {
        musicPlayerCallback?.onSongSelected(song)
    }

    /**
     * Called when the fragment is no longer attached to its activity.
     */
    override fun onDetach() {
        super.onDetach()
        musicPlayerCallback = null
    }

    // Uncomment if you need to release resources when the fragment is destroyed
    // override fun onDestroy() {
    //     super.onDestroy()
    //     if (::music.isInitialized) {
    //         music.release()
    //     }
    // }
}