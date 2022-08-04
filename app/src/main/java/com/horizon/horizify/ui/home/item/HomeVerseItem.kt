package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeVerseItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class HomeVerseItem : BindableItem<HomeVerseItemBinding>() {
    override fun bind(viewBinding: HomeVerseItemBinding, position: Int) {
        with(viewBinding){

        }
    }

    override fun getLayout(): Int = R.layout.home_verse_item

    override fun initializeViewBinding(view: View): HomeVerseItemBinding = HomeVerseItemBinding.bind(view)
}