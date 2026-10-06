package com.example.movieexplorer.data.remote

import com.example.movieexplorer.data.model.SearchResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeApi {
    // GET https://api.tvmaze.com/search/shows?q={query}
    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<SearchResult>
}

object TvMazeService {
    // Dibuat sekali saja (lazy) lalu dipakai ulang.
    val api: TvMazeApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvMazeApi::class.java)
    }
}
