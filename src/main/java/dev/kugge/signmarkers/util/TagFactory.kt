package dev.kugge.signmarkers.util

import dev.kugge.signmarkers.models.Tag
import dev.kugge.signmarkers.models.TagList

class TagFactory {
    var tagRegex = Regex("\\[(\\w+)(?:=(.*?))?]")

    fun parseTags(text: String): TagList {
        val tags = TagList()
        tagRegex.findAll(text).forEach { match ->
            println(match.value);
            val name = match.groupValues[1]
            val value = match.groupValues.getOrNull(2)
            tags.add(Tag(name, value))
        }
        return tags;
    }

    fun stripTags(text: String): String {
        return text.replace(tagRegex, "")
    }
}