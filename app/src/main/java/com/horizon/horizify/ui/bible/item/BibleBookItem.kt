package com.horizon.horizify.ui.bible.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.BibleBookItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class BibleBookItem(private val book: String) : BindableItem<BibleBookItemBinding>() {
    override fun bind(viewBinding: BibleBookItemBinding, position: Int) {
        with(viewBinding) {
            txtBook.text = book
        }
    }

    override fun getLayout(): Int = R.layout.bible_book_item

    override fun initializeViewBinding(view: View): BibleBookItemBinding = BibleBookItemBinding.bind(view)
}