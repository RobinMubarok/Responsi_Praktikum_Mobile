# Movie Explorer

Aplikasi Android untuk mencari film/serial TV berdasarkan judul menggunakan [TVmaze API](https://www.tvmaze.com/api).
Dibuat dengan Kotlin, Jetpack Compose, Material 3, Navigation Compose, dan arsitektur MVVM.

## Fitur
- Search berdasarkan judul (tombol Search / tombol Search di keyboard)
- Daftar hasil (LazyColumn): judul, tahun rilis, rating, genre
- Detail: judul, tahun, rating, genre, ringkasan
- Loading, error (offline, server error, hasil kosong, query kosong), dan fallback `N/A` untuk data null

## Screenshot & GIF


## Struktur MVVM
```
UI (HomeScreen, DetailScreen) -> ShowViewModel -> ShowRepository -> TvMazeApi (Retrofit) -> TVmaze
```
| Layer | File | Tugas |
|---|---|---|
| UI | `ui/screen/*`, `ui/AppNavigation.kt`, `ui/theme/*` | Menampilkan state & mengirim aksi user |
| ViewModel | `viewmodel/ShowViewModel.kt` | Menyimpan `UiState`, menjalankan coroutine |
| Repository | `data/repository/ShowRepository.kt` | Mengambil data dari API (Dispatchers.IO) |
| API | `data/remote/TvMazeApi.kt` | Definisi endpoint Retrofit |
| Model | `data/model/Show.kt` | Data class + helper null-safe |

## Penggunaan API
`GET https://api.tvmaze.com/search/shows?q={query}` — respons berupa list `{ score, show }`.
Hanya field `id, name, premiered, rating.average, genres, summary` yang dipakai.
Detail memakai data hasil pencarian (dicari berdasarkan `id` dari navigation argument), jadi tanpa request tambahan.

## Menjalankan
Buka folder ini di Android Studio, tunggu Gradle sync, lalu Run. Atau: `./gradlew assembleDebug`.

## Link
https://www.youtube.com/playlist?list=PLPiK3OYFs-AE
