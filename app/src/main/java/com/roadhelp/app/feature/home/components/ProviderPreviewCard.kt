package com.roadhelp.app.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roadhelp.app.core.design.EmergencyRed
import com.roadhelp.app.core.design.Ink
import com.roadhelp.app.core.design.MutedInk
import com.roadhelp.app.core.design.RoadTeal
import com.roadhelp.app.core.design.White
import com.roadhelp.app.domain.model.RoadsideProvider

@Composable
fun ProviderPreviewCard(provider: RoadsideProvider) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(provider.name, style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold, color = Ink)
                    Text(provider.category.label, style = MaterialTheme.typography.bodySmall, color = MutedInk)
                }
                Text(formatDistance(provider.distanceMeters), color = RoadTeal, fontWeight = FontWeight.Bold)
            }
            provider.address?.let {
                Text(it, style = MaterialTheme.typography.bodySmall, color = MutedInk)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                provider.rating?.let {
                    Text("★ " + "%.1f".format(it), style = MaterialTheme.typography.labelMedium)
                }
                Text("SAMPLE", style = MaterialTheme.typography.labelSmall,
                    color = EmergencyRed, fontWeight = FontWeight.Bold)
            }
            Text(
                "Contact options appear when a provider source supplies verified details.",
                style = MaterialTheme.typography.bodySmall,
                color = MutedInk
            )
        }
    }
}

private fun formatDistance(meters: Int): String =
    if (meters < 1_000) "$meters m" else "%.1f km".format(meters / 1_000.0)
