package com.horizon.horizify.modules

import com.horizon.horizify.service.NetworkService
import org.koin.dsl.module

val serviceModules = module {
    single { NetworkService() }
}


