package com.horizon.horizify.ui.menu

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.menu.item.MenuBodyItemFragment

class MenuBodyFragment : GroupieFragment() {
    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        val bodyItem = MenuBodyItemFragment()
        with(root) {
            setBody(bodyItem)
        }
    }
}