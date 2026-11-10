package com.roadhelp.app.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roadhelp.app.core.design.Ink
import com.roadhelp.app.domain.model.ProviderCategory

@Composable
fun CategorySelector(
    selectedCategory: ProviderCategory,
    onCategorySelected: (ProviderCategory) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Find a service",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Ink
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(ProviderCategory.entries.toList()) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { onCategorySelected(category) },
                    label = { Text(category.symbol + "  " + category.label) }
                )
            }
        }
    }
}
