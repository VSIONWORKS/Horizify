package com.horizon.horizify.ui.bible.viewModel

import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.ui.bible.model.BibleModel
import com.horizon.horizify.ui.bible.repository.BibleRepository
import com.horizon.horizify.utils.BibleVersion
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

class BibleViewModel(val repository: BibleRepository) : BaseViewModel(), IBibleViewModel {

    private var currentBible: BibleModel = BibleModel()

    override val pageState = MutableStateFlow<PageState>(PageState.Completed)

    override fun saveBible(bible: BibleModel) {
        safeLaunch(Dispatchers.IO){
            repository.saveCurrentBible(bible)
        }
    }

    init {
        pageState.value = PageState.Loading
        safeLaunch(Dispatchers.IO) {
            val bible = repository.getCurrentBible()
            if (bible != null) {
                currentBible = bible
                pageState.value = PageState.Completed
            }
            else getBible(BibleVersion.ENGLISH_STANDARD_VERSION)
        }
    }

    override fun getBible(version: BibleVersion) {
        safeLaunch(Dispatchers.IO) {
            pageState.value = PageState.Loading
            currentBible = repository.fetchBible(version)
            pageState.value = PageState.Completed
        }
    }

    override fun currentBible(): BibleModel = currentBible
}