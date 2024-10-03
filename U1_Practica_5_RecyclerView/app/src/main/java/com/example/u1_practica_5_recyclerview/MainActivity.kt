package com.example.u1_practica_5_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.u1_practica_5_recyclerview.fragments.SongListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SongListFragment())
                .commit()
        }
    }
}