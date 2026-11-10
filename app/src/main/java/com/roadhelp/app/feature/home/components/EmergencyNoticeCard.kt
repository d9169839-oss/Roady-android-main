package com.roadhelp.app.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roadhelp.app.core.design.EmergencyRed
import com.roadhelp.app.core.design.Ink
import com.roadhelp.app.core.design.MutedInk
import com.roadhelp.app.core.design.White

@Composable
fun EmergencyNoticeCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F0))
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(36.dp).background(White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("!", color = EmergencyRed, fontWeight = FontWeight.Bold)
            }
            Column(Modifier.padding(start = 12.dp)) {
                Text("Emergency first", color = Ink, fontWeight = FontWeight.SemiBold)
                Text(
                    "If you are in immediate danger, contact local emergency services.",
                    color = MutedInk,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
