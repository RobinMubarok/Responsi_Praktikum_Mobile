package com.example.movieexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.data.model.Show
import com.example.movieexplorer.data.repository.ShowRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Satu sumber kebenaran untuk seluruh state UI.
data class UiState(
    val query: String = "",
    val shows: List<Show> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedShow: Show? = null
)

class ShowViewModel(
    private val repository: ShowRepository = ShowRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
    }

    // Dipanggil hanya saat user menekan tombol Search (bukan tiap karakter).
    fun search() {
        val query = _uiState.value.query.trim()
        if (query.isEmpty()) {
            _uiState.update { it.copy(error = "Masukkan judul film atau serial terlebih dahulu.") }
            return
        }

        searchJob?.cancel() // batalkan request lama jika user menekan Search lagi
        searchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val results = repository.searchShows(query)
                _uiState.update {
                    it.copy(
                        shows = results,
                        isLoading = false,
                        error = if (results.isEmpty()) "Tidak ada hasil untuk \"$query\"." else null
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                showError("Tidak ada koneksi internet. Periksa jaringan kamu.")
            } catch (e: HttpException) {
                showError("Server bermasalah (kode ${e.code()}). Coba lagi nanti.")
            } catch (e: Exception) {
                showError("Terjadi kesalahan: ${e.message ?: "tidak diketahui"}")
            }
        }
    }

    // Detail diambil dari hasil pencarian yang sudah ada, jadi tanpa request tambahan.
    fun selectShow(showId: Int) {
        val show = _uiState.value.shows.firstOrNull { it.id == showId }
        _uiState.update { it.copy(selectedShow = show) }
    }

    private fun showError(message: String) {
        _uiState.update { it.copy(isLoading = false, error = message) }
    }
}
