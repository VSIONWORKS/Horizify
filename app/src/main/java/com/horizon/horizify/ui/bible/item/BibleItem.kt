package com.horizon.horizify.ui.bible.item

import android.annotation.SuppressLint
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
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
import com.horizon.horizify.utils.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem


class BibleItem(val viewModel: BibleViewModel, val activity: FragmentActivity, val context: Context) : BindableItem<BibleFragmentBinding>() {

    lateinit var dialog: BottomSheetDialog
    private lateinit var tts: TextToSpeech
    private val bibleAdapter = GroupAdapter<GroupieViewHolder>()

    var bible by BindableItemObserver(BibleModel())

    private var audioOn: Boolean = false
    private var scriptToAudio: ArrayList<String> = arrayListOf()

    @SuppressLint("ClickableViewAccessibility")
    override fun bind(viewBinding: BibleFragmentBinding, position: Int) {
        with(viewBinding) {
            if (bible.books.isNotEmpty()) {
                bibleBook.text = bible.currentBook
                bibleVersionDescription.text = bible.versionLong

                tts = TextToSpeechHandler.setUpSpeechEngine(activity)

                setUpBottomSheet()
                setScripture()
                setUpButtons()

//                /* setup scrollview on touch */
                svScript.setOnTouchListener { view, motionEvent ->
                    view.parent.requestDisallowInterceptTouchEvent(true)
                    false
                }
//                txtVerses.movementMethod = ScrollingMovementMethod.getInstance()
            }
        }
    }

    override fun getLayout(): Int = R.layout.bible_fragment
    override fun initializeViewBinding(view: View): BibleFragmentBinding = BibleFragmentBinding.bind(view)

    private fun getBookIndex(): Int = bible.books.indexOfFirst { it.book == bible.currentBook }
    private fun getChapterIndex(): Int = bible.books[getBookIndex()].chapters.indexOfFirst { it.chapter == bible.currentChapter }

    private fun BibleFragmentBinding.setUpBottomSheet() {
        dialog = BottomSheetDialog(root.context, R.style.CustomBottomSheetDialogTheme)
        val sheetBinding = BibleBottomsheetBinding.inflate(context.layoutInflater)

        with(dialog) {
            with(sheetBinding) {
                imgSelectBook.setOnClickListener {
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    setBooksRecyclerView(sheetBinding, bible.books)
                    sheetHeader.text = SELECT_BOOK
                    svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            var bookList: List<Book> = bible.books.filter { it.book.containsIgnoreCase(newText ?: EMPTY_STRING) }
                            setBooksRecyclerView(sheetBinding, bookList)
                            return false
                        }
                    })
                    show()
                }
                lblChapter.setOnClickListener {
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    setChaptersRecyclerView(sheetBinding, dialog)
                    sheetHeader.text = SELECT_CHAPTER
                    show()
                }
                sheetClose.setOnClickListener { dismiss() }
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
        }
    }

    private fun BibleFragmentBinding.setBooksRecyclerView(binding: BibleBottomsheetBinding, books: List<Book>) {
        with(binding) {
            svSearch.isVisible = true
            rvBooks.apply {
                adapter = bibleAdapter.apply {
                    bibleAdapter.clear()
                    books.forEach {
                        add(
                            BibleBookItem(
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
                scrollToPosition(getBookIndex())
            }
        }
    }

    private fun BibleFragmentBinding.setChaptersRecyclerView(binding: BibleBottomsheetBinding, dialog: BottomSheetDialog) {
        val chapters = bible.books[getBookIndex()].chapters
        with(binding) {
            svSearch.isVisible = false
            rvBooks.apply {
                adapter = bibleAdapter.apply {
                    bibleAdapter.clear()
                    chapters.forEach {
                        add(
                            BibleChapterItem(
                                currentChapter = bible.currentChapter,
                                chapter = it.chapter,
                                onClick = ItemActionWithValue { chapter ->
                                    bible.currentChapter = chapter
                                    setScripture()
                                    dialog.dismiss()
                                }
                            )
                        )
                    }
                }
                layoutManager = GridLayoutManager(context, 4)
                scrollToPosition(getChapterIndex())
            }
        }
    }

    private fun BibleFragmentBinding.setScripture() {
        stopTextToSpeech()
        val bookIndex = getBookIndex()
        val currentChapterIndex = bible.currentChapter.toInt() - 1
        val chapter = bible.books[bookIndex].chapters[currentChapterIndex]
        lblChapter.text = "$CHAPTER ${bible.books[getBookIndex()].chapters[currentChapterIndex].chapter}"

        // always start to top
        svScript.pageScroll(View.FOCUS_UP)
        svScript.smoothScrollTo(0, 0)

//        rvScript.apply {
//            adapter = scriptAdapter.apply {
//                scriptAdapter.clear()
//                add(
//                    BibleScriptItem(
//                        bookIndex = bookIndex,
//                        currentChapterIndex = currentChapterIndex,
//                        chapter = chapter,
//                        context = context
//                    )
//                )
//            }
//
//            layoutManager = LinearLayoutManager(context)
//        }


        scriptToAudio = arrayListOf()
        txtVerses.text = EMPTY_STRING
        var strVerse = txtVerses.text
        chapter.verses.forEach {
            scriptToAudio.add(it.script)

            val str = "${it.verse} ${it.script}\n"
            val spannedStr = formatVerse(it.verse, str)
            strVerse = TextUtils.concat(strVerse, spannedStr)
        }
        txtVerses.text = strVerse

        // save current state of bible
        viewModel.saveBible(bible)
    }

    private fun formatVerse(verseNo: String, text: String): SpannableString? {

        val endCount = verseNo.count()
        val spannedString = SpannableString(text)

        spannedString.setSpan(TopAlignSuperscriptSpan(0.1f), 0, endCount, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannedString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.verse)), 0, endCount, 0);

        return spannedString
    }

    private fun BibleFragmentBinding.setUpButtons() {

        imgAudio.setOnClickListener {
            if (audioOn) stopTextToSpeech()
            else startTextToSpeech()
        }

        btnPrev.setOnClickListener {
            stopTextToSpeech()
            val chapter = bible.currentChapter.toInt()
            if (chapter > 1) bible.currentChapter = (chapter - 1).toString()
            setScripture()
        }
        btnNext.setOnClickListener {
            stopTextToSpeech()
            val chapter = bible.currentChapter.toInt()
            val chapters = bible.books[getBookIndex()].chapters
            if (chapter < chapters.size) bible.currentChapter = (chapter + 1).toString()
            setScripture()
        }
    }

    private fun BibleFragmentBinding.startTextToSpeech() {
        imgAudio.imageTintList = ContextCompat.getColorStateList(context, R.color.selected_item_color)
        audioOn = true
        tts.setMessages(scriptToAudio)
        tts.speak()
    }

    private fun BibleFragmentBinding.stopTextToSpeech() {
        imgAudio.imageTintList = ContextCompat.getColorStateList(context, R.color.unselected_item_color)
        audioOn = false
        tts.stopEngine()
    }

    private fun back() {

    }

    companion object {
        const val EMPTY_STRING = ""
        const val SELECT_BOOK = "Select Book"
        const val SELECT_CHAPTER = "Select Chapter"
        const val CHAPTER = "Chapter"
        const val CHAPTER_NO = "1"
    }
}