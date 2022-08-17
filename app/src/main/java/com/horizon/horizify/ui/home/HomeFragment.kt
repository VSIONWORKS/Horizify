package com.horizon.horizify.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.home.item.*
import com.horizon.horizify.ui.home.viewmodel.HomeViewModel
import com.horizon.horizify.utils.Constants.CHECK_IN_URL
import com.horizon.horizify.utils.Constants.GIVING_URL
import com.horizon.horizify.utils.ItemAction
import com.horizon.horizify.utils.ItemActionWithPosition
import com.horizon.horizify.utils.PageId
import com.horizon.horizify.utils.SingletonHandler
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class HomeFragment : GroupieFragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val homeHeaderItem by inject<HomeHeaderItem> { parametersOf(homeViewModel) }

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        with(root) {

//            var bodyItem = HomeBodyItem()
//            var verseItem = HomeVerseItem()
            var footerItem = HomeFooterItem()

            var bodyRecyclerItem = HomeBodyRecyclerItem(
                onClick = ItemActionWithPosition {
                    when(it) {
                        CardEnum.CHECK_IN.ordinal -> {
                            homeViewModel.saveWebUrl(CHECK_IN_URL)
                            navigateToPage(PageId.WEBVIEW)
                        }
                        CardEnum.GIVING.ordinal -> {
                            homeViewModel.saveWebUrl(GIVING_URL)
                            navigateToPage(PageId.WEBVIEW)
                        }
                        CardEnum.LOCATION.ordinal -> navigateToPage(PageId.LOCATION)
                        CardEnum.CONNECT.ordinal -> navigateToPage(PageId.LOCATION)
                    }
                }
            )

            val bottomItem = HomeBottomItem(
                onClick = ItemAction {
                    homeViewModel.saveWebUrl(GIVING_URL)
                    navigateToPage(PageId.WEBVIEW)
                }
            )

            var locationCheckInItem = HomeLocationCheckIn(
                onClickLocation = ItemAction { navigateToPage(PageId.LOCATION) },
                onClickCheckIn = ItemAction {
                    homeViewModel.saveWebUrl(CHECK_IN_URL)
                    navigateToPage(PageId.WEBVIEW)
                }
            )

            setHeader(homeHeaderItem)
            setBody(bodyRecyclerItem, bottomItem)
            setFooter(footerItem)

//            bodyItem.clickListener = ItemAction { navigateToPage(PageId.CALENDAR) }
        }
    }

    override fun onPause() {
        super.onPause()
        SingletonHandler.clearCarouselHandler()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("destroy", "here")
    }
}