package com.example.u1_practica_5_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.u1_practica_5_recyclerview.adapter.*

class MainActivity : AppCompatActivity() {
    private lateinit var songAdapter: SongsAdapter
    private lateinit var userRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decorator = DividerItemDecoration(this, manager.orientation)

        // Get the SongProvider instance using the application context
        val songProvider = SongProvider.getInstance(applicationContext)

        songAdapter = SongsAdapter(songProvider.listaSongs, ::onItemSelected)
        userRecycler = findViewById(R.id.user_list)
        userRecycler.layoutManager = manager
        userRecycler.adapter = songAdapter

        userRecycler.addItemDecoration(decorator)
    }

    private fun onItemSelected(song: Song) {
        val msg = "${song.name} by ${song.artist}"
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}