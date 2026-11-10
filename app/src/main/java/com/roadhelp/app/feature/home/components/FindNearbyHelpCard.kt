package com.roadhelp.app.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roadhelp.app.core.design.Ink
import com.roadhelp.app.core.design.RoadTeal
import com.roadhelp.app.core.design.White

@Composable
fun FindNearbyHelpCard(onFindNearbyHelp: () -> Unit) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Ink)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                "Need help on the road?",
                style = MaterialTheme.typography.titleLarge,
                color = White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Discover nearby towing, tyre, battery and repair services.",
                color = White.copy(alpha = 0.82f)
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onFindNearbyHelp,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RoadTeal)
            ) {
                Text("Find nearby help", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
