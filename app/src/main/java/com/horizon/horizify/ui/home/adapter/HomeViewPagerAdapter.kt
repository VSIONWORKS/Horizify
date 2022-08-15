package com.horizon.horizify.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.horizon.horizify.databinding.HomeHeaderCardBinding
import com.horizon.horizify.ui.home.model.HeaderCardModel


class HomeViewPagerAdapter(private val cards: List<HeaderCardModel>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeHeaderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount(): Int = cards.size
}

class ViewHolder(private val binding: HomeHeaderCardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(card: HeaderCardModel) {
        binding.cardHeader.setImageResource(card.drawableId)
    }
}