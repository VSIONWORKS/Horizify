package com.horizon.horizify.ui.bible.repository

import android.content.Context
import com.google.gson.Gson
import com.horizon.horizify.base.SharedPreference
import com.horizon.horizify.extensions.get
import com.horizon.horizify.extensions.saveObject
import com.horizon.horizify.ui.bible.model.BibleModel
import com.horizon.horizify.utils.BibleVersion
import com.horizon.horizify.utils.Constants
import com.horizon.horizify.utils.Constants.CURRENT_BIBLE
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException


class BibleRepositoryImpl(private val context: Context, private val prefs: SharedPreference) : BibleRepository {

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

            var books: ArrayList<BibleModel.Book> = arrayListOf()
            var chapters: ArrayList<BibleModel.Chapter> = arrayListOf()
            var verses: ArrayList<BibleModel.Verse> = arrayListOf()

            var book = ""
            var chapter = ""

            var event = parser.eventType
            while (event != XmlPullParser.END_DOCUMENT) {

                if (event == XmlPullParser.START_TAG) {

                    when (parser.name) {
                        TAG_B, TAG_BOOK -> book = parser.getAttributeValue(0)
                        TAG_C, TAG_CHAPTER -> chapter = parser.getAttributeValue(0)
                        TAG_V, TAG_VERSE -> {
                            val verse = parser.getAttributeValue(0)
                            val script = parser.nextText()
                            verses.add(BibleModel.Verse(verse, script))
                        }
                    }
                } else if (event == XmlPullParser.END_TAG) {
                    when (parser.name) {
                        TAG_B, TAG_BOOK -> {
                            books.add(BibleModel.Book(book = book, chapters = chapters))
                            chapters.clear()
                        }
                        TAG_C, TAG_CHAPTER -> {
                            chapters.add(BibleModel.Chapter(chapter = chapter, verses = verses))
                            verses.clear()
                        }
                    }
                }
                event = parser.next()
            }

            val bible = BibleModel(
                versionLong = file.longDescription,
                versionShort = file.shortDescription,
                books = books
            )

            // save selected bible version
            prefs.removeValue(CURRENT_BIBLE)
            prefs.saveObject(CURRENT_BIBLE, bible)

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