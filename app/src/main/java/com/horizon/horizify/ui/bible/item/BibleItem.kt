package com.horizon.horizify.ui.bible.item

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
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
import com.horizon.horizify.utils.TopAlignSuperscriptSpan
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class BibleItem(val viewModel: BibleViewModel, val context: Context) : BindableItem<BibleFragmentBinding>() {

    private lateinit var dialog: BottomSheetDialog
    private val booksAdapter = GroupAdapter<GroupieViewHolder>()

    var bible by BindableItemObserver(BibleModel())

    override fun bind(viewBinding: BibleFragmentBinding, position: Int) {
        with(viewBinding) {
            if (bible.books.isNotEmpty()) {
                bibleBook.text = bible.currentBook
                bibleVersionDescription.text = bible.versionLong

                setUpSelectBookBottomSheet()
                setScripture()
                setUpPrevNextButtons()

                /* setup scrollview on touch */
                svScript.setOnTouchListener { view, motionEvent ->
                    view.parent.requestDisallowInterceptTouchEvent(true)
                    false
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.bible_fragment
    override fun initializeViewBinding(view: View): BibleFragmentBinding = BibleFragmentBinding.bind(view)

    private fun getBookIndex(): Int = bible.books.indexOfFirst { it.book == bible.currentBook }

    private fun BibleFragmentBinding.setUpSelectBookBottomSheet() {
        imgSelectBook.setOnClickListener {
            dialog = BottomSheetDialog(root.context, R.style.CustomBottomSheetDialogTheme)
            val sheetBinding = BibleBottomsheetBinding.inflate(context.layoutInflater)

            with(dialog) {
                with(sheetBinding) {
                    setBooksRecyclerView(sheetBinding, bible.books)
                    sheetClose.setOnClickListener { dismiss() }
                    svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            var bookList: List<Book> = bible.books.filter { it.book.containsIgnoreCase(newText ?: "") }
                            setBooksRecyclerView(sheetBinding, bookList)
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
                        Log.e("newState:", "$newState")
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) dismiss()
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                })
                show()
            }
        }
    }

    private fun BibleFragmentBinding.setBooksRecyclerView(binding: BibleBottomsheetBinding, books: List<Book>) {
        with(binding) {
            rvBooks.apply {
                adapter = booksAdapter.apply {
                    booksAdapter.clear()
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
                                    bible.currentChapter = CHAPTER_NO
                                    setScripture()
                                }
                            )
                        )
                    }
                }
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun BibleFragmentBinding.setScripture() {
        val bookIndex = getBookIndex()
        val currentChapterIndex = bible.currentChapter.toInt() - 1
        val chapter = bible.books[bookIndex].chapters[currentChapterIndex]
        lblChapter.text = "$CHAPTER ${bible.books[getBookIndex()].chapters[currentChapterIndex].chapter}"

        txtVerses.text = ""
        var strVerse = txtVerses.text
        chapter.verses.forEach {
            val str = "${it.verse} ${it.script}\n"
            val spannedStr = formatVerse(it.verse, str)
            strVerse= TextUtils.concat(strVerse, spannedStr)
        }
        txtVerses.text = strVerse
    }

    private fun formatVerse(verseNo: String, text: String): SpannableString? {

        val endCount = verseNo.count()
        val spannedString = SpannableString(text)

        spannedString.setSpan(TopAlignSuperscriptSpan(0.1f), 0, endCount, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannedString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context,R.color.verse)), 0, endCount, 0);

        return spannedString
    }

    private fun BibleFragmentBinding.setUpPrevNextButtons() {
        btnPrev.setOnClickListener {
            val chapter = bible.currentChapter.toInt()
            if (chapter > 1) bible.currentChapter = (chapter-1).toString()
            setScripture()
        }
        btnNext.setOnClickListener {
            val chapter = bible.currentChapter.toInt()
            val chapters = bible.books[getBookIndex()].chapters
            if (chapter < chapters.size) bible.currentChapter = (chapter+1).toString()
            setScripture()
        }
    }

    companion object {
        const val CHAPTER = "Chapter"
        const val CHAPTER_NO = "1"
    }

}