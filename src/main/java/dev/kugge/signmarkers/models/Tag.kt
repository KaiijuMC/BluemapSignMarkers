package dev.kugge.signmarkers.models


class Tag(val name: String, val value: String?) {
    fun contains(str: String): Boolean {
        return name.contains(str);
    }
}