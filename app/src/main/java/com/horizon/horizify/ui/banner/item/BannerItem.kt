package com.horizon.horizify.ui.banner.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.common.model.BannerModel
import com.horizon.horizify.databinding.BannerFragmentBinding
import com.horizon.horizify.extensions.loadFitCenter
import com.horizon.horizify.utils.ItemActionWithValue
import com.xwray.groupie.viewbinding.BindableItem

class BannerItem(private val model: BannerModel, private val onClickLink: ItemActionWithValue<BannerModel>) : BindableItem<BannerFragmentBinding>() {

    override fun bind(viewBinding: BannerFragmentBinding, position: Int) {
        with(viewBinding) {
            imageBanner.loadFitCenter(model.image)
            textDescription.text = model.description
            textLink.text = model.linkCaption
            textLink.setOnClickListener { onClickLink.actionCallback.invoke(model) }
        }
    }

    override fun getLayout(): Int = R.layout.banner_fragment

    override fun initializeViewBinding(view: View): BannerFragmentBinding = BannerFragmentBinding.bind(view)
}