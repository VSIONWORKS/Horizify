package com.horizon.horizify.ui.banner

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.browseExternal
import com.horizon.horizify.extensions.handleBackPress
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.banner.item.BannerItem
import com.horizon.horizify.ui.home.viewmodel.HomeViewModel
import com.horizon.horizify.utils.ItemActionWithValue
import com.horizon.horizify.utils.PageId
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BannerFragment: GroupieFragment() {

    private val bannerViewModel: HomeViewModel by sharedViewModel()

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        root.apply {
            setHeader(smileToolbar)
            smileToolbar.model.title = bannerViewModel.selectedBanner.value.title
            setBody(
                BannerItem(
                    model = bannerViewModel.selectedBanner.value,
                    onClickLink = ItemActionWithValue { banner ->
                        if (banner.isExternalBrowser) {
                            banner.link.browseExternal(requireContext())
                        }
                        else {
                            bannerViewModel.saveWebUrl(banner.link)
                            navigateToPage(PageId.WEBVIEW)
                        }
                    }
                )
            )
        }
        handleBackPress { popBack() }
    }
}