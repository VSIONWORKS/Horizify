package com.horizon.horizify.ui.bible.repository

import com.horizon.horizify.ui.bible.model.BibleModel
import com.horizon.horizify.utils.BibleVersion

interface BibleRepository {
    suspend fun getCurrentBible() : BibleModel?
    suspend fun fetchBible(version: BibleVersion) : BibleModel
}