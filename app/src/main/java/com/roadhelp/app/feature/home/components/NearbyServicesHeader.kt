package com.roadhelp.app.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roadhelp.app.core.design.Ink
import com.roadhelp.app.core.design.MutedInk
import com.roadhelp.app.domain.model.ProviderCategory

@Composable
fun NearbyServicesHeader(onShowAll: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("Nearby services", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, color = Ink)
                Text("Within 25 km · nearest first",
                    style = MaterialTheme.typography.bodySmall, color = MutedInk)
            }
            TextButton(onClick = onShowAll) { Text(ProviderCategory.ALL.label) }
        }
        Text(
            "Sample listings · live results appear after location and provider search are connected.",
            style = MaterialTheme.typography.bodySmall,
            color = MutedInk
        )
    }
}
