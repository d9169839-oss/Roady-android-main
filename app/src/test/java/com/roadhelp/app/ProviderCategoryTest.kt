package com.roadhelp.app
import com.roadhelp.app.domain.model.ProviderCategory
import org.junit.Assert.assertEquals
import org.junit.Test
class ProviderCategoryTest {
    @Test fun allCategoryIsFirstAndUsesCanonicalApiValue() {
        assertEquals(ProviderCategory.ALL, ProviderCategory.entries.first())
        assertEquals("all", ProviderCategory.ALL.apiValue)
    }
    @Test fun categoryApiValuesAreUnique() {
        val values = ProviderCategory.entries.map { it.apiValue }
        assertEquals(values.size, values.toSet().size)
    }
}
