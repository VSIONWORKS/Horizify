package com.horizon.horizify.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.horizon.horizify.Extensions.setBody
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.modules.HelloSayer
import com.horizon.horizify.ui.home.item.HomeBodyItem
import com.horizon.horizify.utils.ItemAction
import com.horizon.horizify.utils.PageId
import org.koin.core.component.inject

class HomeFragment : GroupieFragment() {

    private val test by inject<HelloSayer>()

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        with(root) {
            var bodyItem = HomeBodyItem()
            setBody(bodyItem)

            bodyItem.clickListener = ItemAction { navigateToPage(PageId.CALENDAR) }
            Log.e("test", test.sayHello() )
        }
    }
}