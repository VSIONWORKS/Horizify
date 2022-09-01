package com.horizon.horizify.modules

import com.horizon.horizify.repository.FirebaseRepository
import com.horizon.horizify.repository.FirebaseRepositoryImpl
import com.horizon.horizify.service.NetworkService
import org.koin.dsl.module

val serviceModules = module {
    single { NetworkService() }
    factory<FirebaseRepository> { FirebaseRepositoryImpl() }
}


