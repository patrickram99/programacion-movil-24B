package com.example.u1_practica_5_recyclerview.fragments

import android.content.Context
import android.media.MediaPlayer
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

class SongListFragment : Fragment() {
    private lateinit var songAdapter: SongsAdapter
    private lateinit var userRecycler: RecyclerView
    private var musicPlayerCallback: MusicPlayerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        musicPlayerCallback = context as? MusicPlayerCallback
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_song_list, container, false)
        userRecycler = view.findViewById(R.id.user_list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

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

    private fun onItemSelected(song: Song) {
        musicPlayerCallback?.onSongSelected(song)
    }

    override fun onDetach() {
        super.onDetach()
        musicPlayerCallback = null
    }

/*    override fun onDestroy() {
        super.onDestroy()
        if (::music.isInitialized) {
            music.release()
        }
    }*/
}