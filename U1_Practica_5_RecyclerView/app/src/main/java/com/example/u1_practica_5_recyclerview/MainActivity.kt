package com.example.u1_practica_5_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.u1_practica_5_recyclerview.adapter.*

class MainActivity : AppCompatActivity() {
    private lateinit var userAdapter: SongsAdapter
    private lateinit var userRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decorator = DividerItemDecoration(this, manager.orientation)
        userAdapter = SongsAdapter(SongProvider.instance.listaSongs, ::onItemSelected)
        userRecycler = findViewById(R.id.user_list)
        userRecycler.layoutManager = manager
        userRecycler.adapter = userAdapter

        userRecycler.addItemDecoration(decorator)
    }

    private fun onItemSelected(user: Song) {
        val msg = "${user.name} OMG Evento"
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
