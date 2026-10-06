package com.example.movieexplorer.data.model

// TVmaze membungkus setiap hasil pencarian: { "score": ..., "show": { ... } }
data class SearchResult(val show: Show)

// Hanya field yang benar-benar dipakai aplikasi.
// Field nullable karena TVmaze bisa mengembalikan null.
data class Show(
    val id: Int,
    val name: String,
    val premiered: String?,
    val rating: Rating?,
    val genres: List<String>?,
    val summary: String?
)

data class Rating(val average: Double?)

// Fallback tampilan untuk data null (null safety dengan elvis operator).
val Show.year: String
    get() = premiered?.take(4) ?: "N/A"

val Show.ratingText: String
    get() = rating?.average?.toString() ?: "N/A"

val Show.genreText: String
    get() = genres.orEmpty().joinToString(", ").ifEmpty { "N/A" }

// Summary dari TVmaze berisi tag HTML (<p>, <b>), jadi dibersihkan dulu.
val Show.summaryText: String
    get() = summary?.replace(Regex("<[^>]*>"), "")?.trim()?.ifEmpty { null }
        ?: "Ringkasan tidak tersedia."
