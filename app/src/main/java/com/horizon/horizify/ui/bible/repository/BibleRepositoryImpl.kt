package com.horizon.horizify.ui.bible.repository

import android.content.Context
import com.google.gson.Gson
import com.horizon.horizify.base.SharedPreference
import com.horizon.horizify.extensions.get
import com.horizon.horizify.extensions.saveObject
import com.horizon.horizify.ui.bible.model.BibleModel
import com.horizon.horizify.ui.bible.model.Book
import com.horizon.horizify.ui.bible.model.Chapter
import com.horizon.horizify.ui.bible.model.Verse
import com.horizon.horizify.utils.BibleVersion
import com.horizon.horizify.utils.Constants
import com.horizon.horizify.utils.Constants.CURRENT_BIBLE
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException


class BibleRepositoryImpl(private val context: Context, private val prefs: SharedPreference) : BibleRepository {

    override suspend fun saveCurrentBible(bible: BibleModel) {
        // save selected bible version
        prefs.removeValue(CURRENT_BIBLE)
        prefs.saveObject(CURRENT_BIBLE, bible)
    }

    override suspend fun getCurrentBible(): BibleModel? {
        val gson = Gson()
        val jsonObject = prefs?.get(CURRENT_BIBLE, "")
        val bible = gson.fromJson(jsonObject, BibleModel::class.java) ?: BibleModel()
        return if (bible.books.isNotEmpty()) bible else null
    }

    override suspend fun fetchBible(version: BibleVersion): BibleModel {

        val file = Constants.getBibleVersionName(version)

        try {
            val assetStream = context.assets.open(file.fileName)
            //creating a XmlPull parse Factory instance
            val parserFactory = XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()
            // setting the namespaces feature to false
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            // setting the input to the parser
            parser.setInput(assetStream, null)

            var books: ArrayList<Book> = arrayListOf()
            var chapters: ArrayList<Chapter> = arrayListOf()
            var verses: ArrayList<Verse> = arrayListOf()

            var bookStr = ""
            var chapterStr = ""

            var event = parser.eventType
            while (event != XmlPullParser.END_DOCUMENT) {

                if (event == XmlPullParser.START_TAG) {

                    when (parser.name) {
                        TAG_B, TAG_BOOK -> bookStr = parser.getAttributeValue(0)
                        TAG_C, TAG_CHAPTER -> chapterStr = parser.getAttributeValue(0)
                        TAG_V, TAG_VERSE -> {
                            val verseStr = parser.getAttributeValue(0)
                            val scriptStr = parser.nextText()
                            verses.add(Verse(verse = verseStr, script = scriptStr))
                        }
                    }
                } else if (event == XmlPullParser.END_TAG) {
                    when (parser.name) {
                        TAG_B, TAG_BOOK -> {
                            books.add(Book(book = bookStr, chapters = chapters))
                            chapters = arrayListOf()
                        }
                        TAG_C, TAG_CHAPTER -> {
                            chapters.add(Chapter(chapter = chapterStr, verses = verses))
                            verses = arrayListOf()
                        }
                    }
                }
                event = parser.next()
            }

            val bible = BibleModel(
                versionLong = file.longDescription,
                versionShort = file.shortDescription,
                books = books.toList()
            )

            saveCurrentBible(bible)

            return bible

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        }

        return BibleModel()
    }

    companion object {
        private const val TAG_B = "b"
        private const val TAG_C = "c"
        private const val TAG_V = "v"
        private const val TAG_BOOK = "BIBLEBOOK"
        private const val TAG_CHAPTER = "CHAPTER"
        private const val TAG_VERSE = "VERS"
    }
}