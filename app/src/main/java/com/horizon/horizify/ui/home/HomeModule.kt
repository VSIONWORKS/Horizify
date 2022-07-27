package com.horizon.horizify.ui.home

import com.horizon.horizify.modules.HelloSayer
import org.koin.dsl.module

object HomeModule {
    val dependency = module {
        factory { HelloSayer() }
    }
}