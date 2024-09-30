package dev.kugge.signmarkers.util

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TagFactoryTest {

    @Test
    fun parseTags() {
        val tags = TagFactory().parseTags("[map] This is my map item")
        assertNotNull(tags)
        assert(tags.size > 0)
        assert(tags.contains("map"))
        assert(tags.findByName("map")?.value == "")
    }

    @Test
    fun testThatTagsWithValuesStoreCorrectly(){
        val tags = TagFactory().parseTags("[map=This]")
        assertNotNull(tags)
        assert(tags.size > 0)
        assert(tags.contains("map"))
        assertNotNull(tags.findByName("map")?.value)
        assert(tags.findByName("map")?.value == "This")
        assertFalse(tags.findByName("map")?.value == "Not")
    }
}