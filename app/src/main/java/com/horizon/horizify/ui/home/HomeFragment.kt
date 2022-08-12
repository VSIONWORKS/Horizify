package com.horizon.horizify.ui.home

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.modules.HelloSayer
import com.horizon.horizify.ui.home.item.HomeBodyItem
import com.horizon.horizify.ui.home.item.HomeHeaderItem
import com.horizon.horizify.ui.home.item.HomeLocationCheckin
import com.horizon.horizify.ui.home.item.HomeVerseItem
import com.horizon.horizify.ui.home.viewmodel.HomeViewModel
import com.horizon.horizify.utils.ItemAction
import com.horizon.horizify.utils.PageId
import com.horizon.horizify.utils.SingletonHandler
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class HomeFragment : GroupieFragment() {

    private val test by inject<HelloSayer>()

    private val homeViewModel: HomeViewModel by viewModel()

    private val homeHeaderItem by inject<HomeHeaderItem> { parametersOf(homeViewModel) }

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        with(root) {
            var bodyItem = HomeBodyItem()
            var verseItem = HomeVerseItem()
            var upcomingItem = HomeLocationCheckin()

            setHeader(homeHeaderItem)
            setBody(verseItem, bodyItem)
            setFooter(upcomingItem)

            bodyItem.clickListener = ItemAction { navigateToPage(PageId.CALENDAR) }

        }
    }

    override fun onPause() {
        super.onPause()
        SingletonHandler.clearCarouselHandler()
    }
}