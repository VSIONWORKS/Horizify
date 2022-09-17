package com.horizon.horizify.common.ui.comingsoon.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.ComingSoonFragmentBinding
import com.xwray.groupie.viewbinding.BindableItem

class ComingSoonItem : BindableItem<ComingSoonFragmentBinding>() {

    override fun bind(viewBinding: ComingSoonFragmentBinding, position: Int) {}

    override fun getLayout(): Int = R.layout.coming_soon_fragment

    override fun initializeViewBinding(view: View): ComingSoonFragmentBinding = ComingSoonFragmentBinding.bind(view)
}