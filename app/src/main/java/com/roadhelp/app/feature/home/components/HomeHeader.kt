package com.roadhelp.app.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roadhelp.app.core.design.Ink
import com.roadhelp.app.core.design.MutedInk
import com.roadhelp.app.core.design.RoadTeal
import com.roadhelp.app.core.design.SoftTeal

@Composable
fun HomeHeader() {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text("RoadHelp", fontWeight = FontWeight.Bold, color = Ink)
            Text("Find help. Get moving.", color = MutedInk)
        }
        Box(
            Modifier.size(46.dp).background(SoftTeal, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("RH", color = RoadTeal, fontWeight = FontWeight.Bold)
        }
    }
}
