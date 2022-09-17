package com.horizon.horizify.common.ui.toolbar

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.base.Sticky
import com.horizon.horizify.common.model.ToolbarModel
import com.horizon.horizify.databinding.SmileToolbarFragmentBinding
import com.horizon.horizify.utils.BindableItemObserver
import com.xwray.groupie.viewbinding.BindableItem

class SmileToolbarItem: BindableItem<SmileToolbarFragmentBinding>(), Sticky {

    var model by BindableItemObserver(ToolbarModel())

    override fun bind(viewBinding: SmileToolbarFragmentBinding, position: Int) {
        viewBinding.apply {
            textTitle.text = model.title
        }
    }

    override fun getLayout(): Int = R.layout.smile_toolbar_fragment

    override fun initializeViewBinding(view: View): SmileToolbarFragmentBinding = SmileToolbarFragmentBinding.bind(view)
}