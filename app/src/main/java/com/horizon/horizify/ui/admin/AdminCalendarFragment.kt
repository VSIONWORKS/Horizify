package com.horizon.horizify.ui.admin

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.handleBackPress
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.admin.item.setup.AdminSetupCalendarItem
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminCalendarFragment : GroupieFragment() {

    private val adminViewModel: AdminViewModel by viewModel()

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        root.apply {
            setBody(AdminSetupCalendarItem())
        }

        handleBackPress { onBackPressed() }
    }

    private fun onBackPressed() = popBack()
}