package com.horizon.horizify.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.horizon.horizify.commonModel.BannerModel
import com.horizon.horizify.databinding.HomeHeaderCardBinding
import com.horizon.horizify.extensions.load
import com.horizon.horizify.ui.home.model.HeaderCardModel
import com.horizon.horizify.utils.ItemActionWithValue


class HomeViewPagerAdapter(private val cards: List<HeaderCardModel>, val onClick: ItemActionWithValue<BannerModel>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeHeaderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount(): Int = cards.size
}

class ViewHolder(private val binding: HomeHeaderCardBinding, val onClick: ItemActionWithValue<BannerModel>) : RecyclerView.ViewHolder(binding.root) {
    fun bind(card: HeaderCardModel) {
        with(binding) {
            if (card.isDefault) {
                cardHeader.isVisible = false
                cardHeaderAdd.isVisible = true
            }
            else if (card.drawableId != -1) cardHeader.setImageResource(card.drawableId)
            else cardHeader.load(card.banner.image)

            cardBanner.setOnClickListener {
                onClick.actionCallback(card.banner)
            }
        }
    }
}