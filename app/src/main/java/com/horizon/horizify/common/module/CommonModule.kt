package com.horizon.horizify.common.module

import com.horizon.horizify.common.ui.toolbar.SmileToolbarItem
import org.koin.dsl.module

object CommonModule {
    val dependency = module {
        factory { SmileToolbarItem() }
    }
}