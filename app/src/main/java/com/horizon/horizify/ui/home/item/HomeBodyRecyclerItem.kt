package com.horizon.horizify.ui.home.item

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeBodyRecyclerItemBinding
import com.horizon.horizify.ui.home.CardEnum
import com.horizon.horizify.utils.ItemActionWithPosition
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class HomeBodyRecyclerItem(val onClick: ItemActionWithPosition) : BindableItem<HomeBodyRecyclerItemBinding>() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun bind(viewBinding: HomeBodyRecyclerItemBinding, position: Int) {
        with(viewBinding) {

            rvBodyRecycler.apply {
                adapter = groupAdapter.apply {
                    add(HomeBodyRecyclerCard(type = CardEnum.CHECK_IN, onClick = ItemActionWithPosition { onClick.actionCallback(it) }))
                    add(HomeBodyRecyclerCard(type = CardEnum.GIVING, onClick = ItemActionWithPosition { onClick.actionCallback(it) }))
                    add(HomeBodyRecyclerCard(type = CardEnum.LOCATION, onClick = ItemActionWithPosition { onClick.actionCallback(it) }))
                    add(HomeBodyRecyclerCard(type = CardEnum.CONNECT, onClick = ItemActionWithPosition { onClick.actionCallback(it) }))
                }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

        }
    }

    override fun getLayout(): Int = R.layout.home_body_recycler_item

    override fun initializeViewBinding(view: View): HomeBodyRecyclerItemBinding = HomeBodyRecyclerItemBinding.bind(view)
}