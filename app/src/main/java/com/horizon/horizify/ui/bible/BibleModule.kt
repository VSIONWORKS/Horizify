package com.horizon.horizify.ui.bible

import com.horizon.horizify.ui.bible.item.BibleItem
import com.horizon.horizify.ui.bible.repository.BibleRepository
import com.horizon.horizify.ui.bible.repository.BibleRepositoryImpl
import com.horizon.horizify.ui.bible.viewModel.BibleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object BibleModule {
    val dependency = module {
        factory<BibleRepository> { BibleRepositoryImpl(androidContext(), get()) }
        factory { BibleItem(it[0], it[1], androidContext()) }
        viewModel { BibleViewModel(get()) }
    }
}