package com.horizon.horizify.ui.bible.model

import kotlinx.serialization.Serializable

@Serializable
data class BibleModel(
    val versionLong: String = "",
    val versionShort: String = "",
    val books: List<Book> = emptyList(),
    var currentBook: String = "Genesis", // Default Book
    var currentChapter: String = "1" // Default Chapter
)

@Serializable
data class Book(val book: String = "", val chapters: List<Chapter>)

@Serializable
data class Chapter(val chapter: String = "", val verses: List<Verse>)

@Serializable
data class Verse(val verse: String = "", val script: String = "")
