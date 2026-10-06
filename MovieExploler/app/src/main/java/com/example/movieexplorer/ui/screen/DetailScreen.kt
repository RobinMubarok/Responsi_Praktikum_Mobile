package com.example.movieexplorer.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieexplorer.data.model.genreText
import com.example.movieexplorer.data.model.ratingText
import com.example.movieexplorer.data.model.summaryText
import com.example.movieexplorer.data.model.year
import com.example.movieexplorer.viewmodel.ShowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    showId: Int,
    viewModel: ShowViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val show = uiState.selectedShow

    // Meminta ViewModel mengisi state detail berdasarkan ID dari navigation.
    LaunchedEffect(showId) { viewModel.selectShow(showId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        if (show == null) {
            Box(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Data tidak ditemukan.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(show.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(12.dp))
                Text("Tahun rilis: ${show.year}", style = MaterialTheme.typography.bodyLarge)
                Text("Rating: ${show.ratingText}", style = MaterialTheme.typography.bodyLarge)
                Text("Genre: ${show.genreText}", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(16.dp))
                Text("Ringkasan", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(show.summaryText, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
