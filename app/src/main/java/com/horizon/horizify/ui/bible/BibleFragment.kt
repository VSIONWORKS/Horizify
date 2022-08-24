package com.horizon.horizify.ui.bible

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.collectOnStart
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.bible.item.BibleItem
import com.horizon.horizify.ui.bible.viewModel.BibleViewModel
import com.horizon.horizify.utils.PageState
import com.horizon.horizify.utils.TextToSpeechHandler
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class BibleFragment : GroupieFragment() {

    private var initialLoad = true
    private val bibleViewModel : BibleViewModel by viewModel()

    private val bibleItem by inject<BibleItem> { parametersOf(bibleViewModel, requireActivity()) }

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
//        val item = BibleItem(bibleViewModel, layoutInflater)
        with(root) {
            setBody(bibleItem)
            startCollect()
        }
        disableMainScroll()
    }

    override fun onPause() {
        super.onPause()
        with(root) {
            clear()
            setBody(bibleItem)
        }
        TextToSpeechHandler.shutDownSpeechEngine()
    }

    private fun startCollect() {
        with(bibleViewModel){
            pageState.collectOnStart(viewLifecycleOwner, ::handlePageState)
        }
    }

    private fun handlePageState(state: PageState) {
        binding.apply {
            when (state) {
                PageState.Loading -> showLoader()
                PageState.Completed -> {
                    bibleItem.bible = bibleViewModel.currentBible()
                    hideLoader()
//                    if (!initialLoad){
//                        hideLoader()
//                        navigateToPage(PageId.VIDEO_PLAYER)
//                    }
//                    initialLoad = false
                }
                PageState.Error -> hideLoader()
            }
        }
    }
}