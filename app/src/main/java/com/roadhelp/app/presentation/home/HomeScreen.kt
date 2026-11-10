package com.roadhelp.app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roadhelp.app.core.design.*
import com.roadhelp.app.domain.model.ProviderCategory
import com.roadhelp.app.domain.model.RoadsideProvider

/** Initial visual shell. Listings are sample-only until location and the backend are connected. */
@Composable
fun HomeScreen() {
    var selectedCategory by remember { mutableStateOf(ProviderCategory.ALL) }
    val samples = remember { sampleProviders() }
    val visible = samples.filter { selectedCategory == ProviderCategory.ALL || it.category == selectedCategory }

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            contentPadding = PaddingValues(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("RoadHelp", style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold, color = Ink)
                        Text("Find help. Get moving.", color = MutedInk)
                    }
                    Box(Modifier.size(46.dp).background(SoftTeal, CircleShape), contentAlignment = Alignment.Center) {
                        Text("RH", color = RoadTeal, fontWeight = FontWeight.Bold)
                    }
                }
            }
            item {
                Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Ink)) {
                    Column(Modifier.padding(20.dp)) {
                        Text("Need help on the road?", style = MaterialTheme.typography.titleLarge,
                            color = White, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        Text("Discover nearby towing, tyre, battery and repair services.",
                            color = White.copy(alpha = 0.82f))
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { /* Foreground location flow is the next implementation phase. */ },
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RoadTeal)) {
                            Text("Find nearby help", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
            item {
                Card(shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color(0xFFFFF1F0))) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(36.dp).background(White, CircleShape), contentAlignment = Alignment.Center) {
                            Text("!", color = EmergencyRed, fontWeight = FontWeight.Bold)
                        }
                        Column(Modifier.weight(1f).padding(start = 12.dp)) {
                            Text("Emergency first", color = Ink, fontWeight = FontWeight.SemiBold)
                            Text("If you are in immediate danger, contact local emergency services.",
                                color = MutedInk, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            item {
                Text("Find a service", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, color = Ink)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(ProviderCategory.entries.toList()) { category ->
                        FilterChip(selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = { Text(category.symbol + "  " + category.label) })
                    }
                }
            }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Nearby services", style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold, color = Ink)
                        Text("Within 25 km · nearest first", style = MaterialTheme.typography.bodySmall, color = MutedInk)
                    }
                    TextButton(onClick = { selectedCategory = ProviderCategory.ALL }) { Text("All") }
                }
                Text("Sample listings · live results appear after location and provider search are connected.",
                    style = MaterialTheme.typography.bodySmall, color = MutedInk)
            }
            items(visible, key = { it.id }) { provider -> ProviderPreviewCard(provider) }
            item {
                Text("No account needed. Contact providers directly.",
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    style = MaterialTheme.typography.bodySmall, color = MutedInk)
            }
        }
    }
}

@Composable
private fun ProviderPreviewCard(provider: RoadsideProvider) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(provider.name, style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold, color = Ink)
                    Text(provider.category.label, style = MaterialTheme.typography.bodySmall, color = MutedInk)
                }
                Text(formatDistance(provider.distanceMeters), color = RoadTeal, fontWeight = FontWeight.Bold)
            }
            provider.address?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = MutedInk) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                provider.rating?.let { Text("★ " + "%.1f".format(it), style = MaterialTheme.typography.labelMedium) }
                Text("SAMPLE", style = MaterialTheme.typography.labelSmall,
                    color = EmergencyRed, fontWeight = FontWeight.Bold)
            }
            Text("Contact options appear when a provider source supplies verified details.",
                style = MaterialTheme.typography.bodySmall, color = MutedInk)
        }
    }
}

private fun formatDistance(meters: Int): String =
    if (meters < 1_000) meters.toString() + " m" else "%.1f km".format(meters / 1_000.0)

private fun sampleProviders() = listOf(
    RoadsideProvider("sample-1", "City Tow Assistance", ProviderCategory.TOW, 1_200, "Sample location", null, 4.7, 83, null, previewOnly = true),
    RoadsideProvider("sample-2", "Rapid Tyre Support", ProviderCategory.TYRE, 2_600, "Sample location", null, 4.5, 41, null, previewOnly = true),
    RoadsideProvider("sample-3", "Battery Rescue", ProviderCategory.BATTERY, 4_100, "Sample location", null, 4.8, 26, null, previewOnly = true),
    RoadsideProvider("sample-4", "Roadside Auto Repair", ProviderCategory.MECHANIC, 6_300, "Sample location", null, 4.3, 59, null, previewOnly = true),
    RoadsideProvider("sample-5", "Emergency Fuel Delivery", ProviderCategory.FUEL, 8_700, "Sample location", null, 4.2, 18, null, previewOnly = true)
)
