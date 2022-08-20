package com.horizon.horizify.ui.bible.item

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.horizon.horizify.R
import com.horizon.horizify.databinding.BibleBottomsheetBinding
import com.horizon.horizify.databinding.BibleFragmentBinding
import com.horizon.horizify.extensions.containsIgnoreCase
import com.horizon.horizify.extensions.layoutInflater
import com.horizon.horizify.ui.bible.model.BibleModel
import com.horizon.horizify.ui.bible.model.Book
import com.horizon.horizify.ui.bible.viewModel.BibleViewModel
import com.horizon.horizify.utils.BindableItemObserver
import com.horizon.horizify.utils.ItemActionWithValue
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class BibleItem(val viewModel: BibleViewModel, val context: Context) : BindableItem<BibleFragmentBinding>() {

    private lateinit var dialog : BottomSheetDialog
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    var bible by BindableItemObserver(BibleModel())

    override fun bind(viewBinding: BibleFragmentBinding, position: Int) {
        with(viewBinding) {
            if (bible.books.isNotEmpty()) {
                bibleBook.text = bible.currentBook
                bibleVersionDescription.text = bible.versionLong

                setUpSelectBookBottomSheet()
            }
        }
    }

    override fun getLayout(): Int = R.layout.bible_fragment
    override fun initializeViewBinding(view: View): BibleFragmentBinding = BibleFragmentBinding.bind(view)

    private fun BibleFragmentBinding.setUpSelectBookBottomSheet() {
        imgSelectBook.setOnClickListener {
            dialog = BottomSheetDialog(root.context, R.style.CustomBottomSheetDialogTheme)
            val sheetBinding = BibleBottomsheetBinding.inflate(context.layoutInflater)

            with(dialog) {
                with(sheetBinding) {
                    setRecyclerView(sheetBinding, bible.books)
                    sheetClose.setOnClickListener { dismiss() }
                    svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            var bookList: List<Book> = bible.books.filter { it.book.containsIgnoreCase(newText ?: "") }
                            setRecyclerView(sheetBinding, bookList)
                            return false
                        }
                    })
                }

                // setup dialog
                setContentView(sheetBinding.root)
                setCancelable(false)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.isFitToContents = false
                behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        Log.e("newState:","$newState")
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) dismiss()
                    }
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                })
                show()
            }
        }
    }

    private fun BibleFragmentBinding.setRecyclerView(binding: BibleBottomsheetBinding, books: List<Book>) {
        with(binding) {
            rvBooks.apply {
                adapter = groupAdapter.apply {
                    groupAdapter.clear()
                    books.forEach {
                        add(
                            BibleBookItem(
                                bible = bible,
                                book = it.book,
                                currentBook = bible.currentBook,
                                onClick = ItemActionWithValue { chapterStr ->
                                    bible.currentBook = chapterStr
                                    dialog.dismiss()
                                    bibleBook.text = bible.currentBook
                                    lblChapter.text = "Chapter ${bible.books.first().chapters.first().chapter}"
                                }
                            )
                        )
                    }
                }
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

}