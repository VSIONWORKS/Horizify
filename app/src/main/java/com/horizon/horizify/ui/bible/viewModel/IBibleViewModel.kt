package com.horizon.horizify.ui.bible.viewModel

import com.horizon.horizify.ui.bible.model.BibleModel
import com.horizon.horizify.utils.BibleVersion
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.flow.StateFlow

interface IBibleViewModel {

    val pageState: StateFlow<PageState>

    fun saveBible(bible: BibleModel)
    fun getBible(version: BibleVersion)
    fun currentBible() : BibleModel
}