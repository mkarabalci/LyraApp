package com.turkcell.lyraapp.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.turkcell.lyraapp.ui.theme.LyraAppTheme

@Composable
fun HomeScreen(
    state: HomeContract.State,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .imePadding(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            GreetingRow(greeting = state.greeting, userInitials = state.userInitials)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Ne dinlemek istersin?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuickPlayGrid(playlists = state.quickPlayPlaylists)
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = "Son çalınanlar", actionLabel = "Tümü")
            Spacer(modifier = Modifier.height(12.dp))
            RecentlyPlayedRow(tracks = state.recentlyPlayed)
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = "Senin için çalma listeleri", actionLabel = "Tümü")
            Spacer(modifier = Modifier.height(12.dp))
            RecommendedRow(playlists = state.recommendedPlaylists)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun GreetingRow(greeting: String, userInitials: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = greeting,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp),
            )
            UserAvatar(initials = userInitials)
        }
    }
}

@Composable
private fun UserAvatar(initials: String) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun QuickPlayGrid(playlists: List<HomeContract.PlaylistItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        playlists.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { item ->
                    QuickPlayCard(item = item, modifier = Modifier.weight(1f))
                }
                if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun QuickPlayCard(item: HomeContract.PlaylistItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(64.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(
                        color = Color(item.colorArgb),
                        shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
                    ),
            ) {
                ConcentricCirclesDecoration(modifier = Modifier.fillMaxSize())
            }
            Text(
                text = item.name,
                modifier = Modifier.padding(horizontal = 10.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String, actionLabel: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        if (actionLabel != null) {
            Text(
                text = actionLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun RecentlyPlayedRow(tracks: List<HomeContract.TrackItem>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(end = 8.dp),
    ) {
        items(tracks, key = { it.id }) { track ->
            TrackCard(track = track)
        }
    }
}

@Composable
private fun TrackCard(track: HomeContract.TrackItem) {
    Column(modifier = Modifier.width(150.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(track.colorArgb)),
        ) {
            ConcentricCirclesDecoration(modifier = Modifier.fillMaxSize())
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = track.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = track.artist,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun RecommendedRow(playlists: List<HomeContract.PlaylistItem>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(end = 8.dp),
    ) {
        items(playlists, key = { it.id }) { playlist ->
            RecommendedCard(playlist = playlist)
        }
    }
}

@Composable
private fun RecommendedCard(playlist: HomeContract.PlaylistItem) {
    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(playlist.colorArgb)),
    ) {
        ConcentricCirclesDecoration(
            modifier = Modifier.fillMaxSize(),
            centerXFraction = 0.3f,
            centerYFraction = 0.4f,
        )
        Text(
            text = playlist.name,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
private fun ConcentricCirclesDecoration(
    modifier: Modifier = Modifier,
    centerXFraction: Float = 0.5f,
    centerYFraction: Float = 0.5f,
) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width * centerXFraction, size.height * centerYFraction)
        val maxRadius = size.minDimension * 0.95f
        val ringCount = 5
        for (i in 1..ringCount) {
            drawCircle(
                color = Color.White.copy(alpha = 0.06f * (ringCount - i + 1)),
                radius = maxRadius * i / ringCount,
                center = center,
                style = Stroke(width = 2.dp.toPx()),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreviewLight() {
    LyraAppTheme(darkTheme = false) {
        HomeScreen(
            state = HomeContract.State(
                greeting = "İyi akşamlar",
                userInitials = "ZK",
                quickPlayPlaylists = listOf(
                    HomeContract.PlaylistItem("1", "Türkçe Pop",   0xFF7B5EA7L),
                    HomeContract.PlaylistItem("2", "Uzun yol", 0xFF6466C5L),
                    HomeContract.PlaylistItem("3", "Arabada kopmalık", 0xFFB5852AL),
                    HomeContract.PlaylistItem("4", "Ders çalışırken",       0xFF2E9B8AL),
                    HomeContract.PlaylistItem("5", "Gece modu",    0xFF4E8B5FL),
                    HomeContract.PlaylistItem("6", "Arabesk",   0xFF3B89A0L),
                ),
                recentlyPlayed = listOf(
                    HomeContract.TrackItem("1", "Türkçe Pop", "Aya Benzer", 0xFFB5852AL),
                    HomeContract.TrackItem("2", "Uzun yol",    "Gidiyorum",        0xFF4E8B5FL),
                    HomeContract.TrackItem("3", "Arabada kopmalık",   "Burada Sokaklar",        0xFF2E9B8AL),
                ),
                recommendedPlaylists = listOf(
                    HomeContract.PlaylistItem("7", "Haftalık Keşif", 0xFF6B5B9CL),
                    HomeContract.PlaylistItem("8", "Yerli Top-50",    0xFF4B5FA6L),
                ),
            ),
            onIntent = {},
        )
    }
}
