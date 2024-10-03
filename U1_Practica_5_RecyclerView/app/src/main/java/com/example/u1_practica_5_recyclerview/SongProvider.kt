package com.example.u1_practica_5_recyclerview

class SongProvider private constructor(){
    companion object {
        val instance: SongProvider = SongProvider()
    }

    val listaSongs = arrayListOf<Song>(
        Song("What The Hell", "Avril Lavigne", "3:40"),
        Song("Greedy", "Tate McRae", "3:00"),
        Song("Apple", "CharliXCX", "3:00"),
        Song("Run Away With Me", "Carly Rae Jepsen", "4:11"),
        Song("New Romantics Taylor's Version", "Taylor Swift", "3:50"),
    )
}