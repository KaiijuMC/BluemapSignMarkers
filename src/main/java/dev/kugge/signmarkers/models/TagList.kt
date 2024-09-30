package dev.kugge.signmarkers.models

class TagList : ArrayList<Tag>() {
    fun contains(name: String): Boolean {
        return this.any { it.name == name }
    }

    fun findByName(name: String): Tag? {
        return this.find { it.name == name }
    }

    operator fun get(name: String): Tag? {
        return findByName(name)
    }
}