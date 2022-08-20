package com.horizon.horizify.ui.bible.item

import android.view.View
import androidx.core.view.isVisible
import com.horizon.horizify.R
import com.horizon.horizify.databinding.BibleBookItemBinding
import com.horizon.horizify.ui.bible.model.BibleModel
import com.horizon.horizify.utils.ItemActionWithValue
import com.xwray.groupie.viewbinding.BindableItem

class BibleBookItem(
    private val bible : BibleModel,
    private val book: String,
    private val currentBook: String,
    private val onClick: ItemActionWithValue<String>
    ) : BindableItem<BibleBookItemBinding>() {

    override fun bind(viewBinding: BibleBookItemBinding, position: Int) {
        with(viewBinding) {
            with(txtBook){
                text = book
                setOnClickListener {
                    onClick.actionCallback(book)
                }
            }
            imgSelected.isVisible = book == currentBook
        }
    }

    override fun getLayout(): Int = R.layout.bible_book_item

    override fun initializeViewBinding(view: View): BibleBookItemBinding = BibleBookItemBinding.bind(view)

    private fun getSelectedChapterIndex(bibleBook: String): Int {
        return bible.books.indexOfFirst { it.book == bibleBook }
    }
}