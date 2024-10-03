package com.example.u1_practica_5_recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.u1_practica_5_recyclerview.R
import com.example.u1_practica_5_recyclerview.Song

class SongsAdapter(var items:MutableList<Song>, val onItemSelected:(Song) -> Unit): RecyclerView.Adapter<SongViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val user = items[position]
        holder.render(user, onItemSelected)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: SongViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val user = items[position]
        holder.render(user, onItemSelected)
    }

}