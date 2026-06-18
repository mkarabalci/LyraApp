package com.turkcell.lyraapp.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.turkcell.lyraapp.data.search.Genre

@Composable
fun SearchScreen(
    state: SearchContract.State,
    onIntent: (SearchContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .imePadding(),
    ) {
        Text(
            text = "Ara",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp),
        )

        SearchBar(
            query = state.query,
            onQueryChanged = { onIntent(SearchContract.Intent.QueryChanged(it)) },
        )

        GenreFilterRow(
            genres = state.allGenres,
            selectedGenreId = state.selectedGenreId,
            onFilterSelected = { onIntent(SearchContract.Intent.GenreFilterSelected(it)) },
            modifier = Modifier.padding(vertical = 12.dp),
        )

        Text(
            text = "Türlere göz at",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(bottom = 12.dp),
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            items(items = state.filteredGenres, key = { it.id }) { genre ->
                GenreCard(
                    genre = genre,
                    onClick = { onIntent(SearchContract.Intent.GenreCardClicked(genre.id)) },
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = {
            Text(
                text = "Şarkı, sanatçı veya albüm",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun GenreFilterRow(
    genres: List<Genre>,
    selectedGenreId: String?,
    onFilterSelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        item {
            val isSelected = selectedGenreId == null
            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelected(null) },
                label = { Text("Hepsi") },
                leadingIcon = if (isSelected) {
                    { Icon(imageVector = Icons.Default.Check, contentDescription = null) }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.onSurface,
                    selectedLabelColor = MaterialTheme.colorScheme.surface,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.surface,
                ),
            )
        }
        items(items = genres, key = { it.id }) { genre ->
            val isSelected = selectedGenreId == genre.id
            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelected(genre.id) },
                label = { Text(genre.name) },
                leadingIcon = if (isSelected) {
                    { Icon(imageVector = Icons.Default.Check, contentDescription = null) }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.onSurface,
                    selectedLabelColor = MaterialTheme.colorScheme.surface,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.surface,
                ),
            )
        }
    }
}

@Composable
private fun GenreCard(
    genre: Genre,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(genre.colorArgb))
            .clickable(onClick = onClick)
            .padding(12.dp),
        contentAlignment = Alignment.TopStart,
    ) {
        Text(
            text = genre.name,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White,
            ),
        )
    }
}
