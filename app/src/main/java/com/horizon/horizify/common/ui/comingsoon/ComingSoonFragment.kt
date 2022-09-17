package com.horizon.horizify.common.ui.comingsoon

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.common.ui.comingsoon.item.ComingSoonItem
import com.horizon.horizify.extensions.handleBackPress
import com.horizon.horizify.extensions.setBody

class ComingSoonFragment: GroupieFragment() {

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        root.apply {
            setBody(ComingSoonItem())
        }
        handleBackPress { onBackPressed() }
    }

    private fun onBackPressed() = popBack()
}