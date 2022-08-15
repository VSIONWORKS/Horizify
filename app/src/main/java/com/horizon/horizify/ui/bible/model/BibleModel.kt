package com.horizon.horizify.ui.bible.model

import kotlinx.serialization.Serializable

@Serializable
data class BibleModel(
    val versionLong: String = "",
    val versionShort: String = "",
    val books: List<Book> = emptyList()
) {
    data class Book(val book: String, val chapters: List<Chapter>)
    data class Chapter(val chapter: String, val verses: List<Verse>)
    data class Verse(val verse: String, val script: String)
}
