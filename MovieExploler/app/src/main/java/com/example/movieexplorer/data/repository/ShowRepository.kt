package com.example.movieexplorer.data.repository

import com.example.movieexplorer.data.model.Show
import com.example.movieexplorer.data.remote.TvMazeApi
import com.example.movieexplorer.data.remote.TvMazeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShowRepository(private val api: TvMazeApi = TvMazeService.api) {

    // Mengambil data dari API lalu membuang pembungkus "SearchResult".
    // Dijalankan di Dispatchers.IO agar tidak membebani main thread.
    suspend fun searchShows(query: String): List<Show> = withContext(Dispatchers.IO) {
        api.searchShows(query).map { it.show }
    }
}
