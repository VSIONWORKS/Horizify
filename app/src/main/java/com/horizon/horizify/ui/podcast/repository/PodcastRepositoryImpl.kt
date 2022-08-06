package com.horizon.horizify.ui.podcast.repository

import com.horizon.horizify.ui.podcast.model.PodcastModel
import com.horizon.horizify.ui.podcast.model.PodcastTrackModel
import com.horizon.horizify.ui.podcast.repository.PodcastRepositoryImpl.TAGS.IMAGE_TAG
import com.horizon.horizify.ui.podcast.repository.PodcastRepositoryImpl.TAGS.ITEM_TAG
import com.horizon.horizify.ui.podcast.repository.PodcastRepositoryImpl.TAGS.LINK_TAG
import com.horizon.horizify.utils.Constants
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.IOException
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class PodcastRepositoryImpl : PodcastRepository {

    private var url = URL(Constants.PODCAST_TRACKS)

    override suspend fun getTrackList(): PodcastModel {
        try {
            var trackList = ArrayList<PodcastTrackModel>()
            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            val doc = docBuilder.parse(InputSource(url.openStream()))
            // reading player tags

            // get podcast cover photo
            val nodeImage = doc.getElementsByTagName(IMAGE_TAG)
            val podcastCover = nodeImage.item(0).firstChild.firstChild.nodeValue

            // get podcast items
            val nodeItemList = doc.getElementsByTagName(ITEM_TAG)

            for (index in 0 until nodeItemList.length) {
                val nodeElement = nodeItemList.item(index) as Element
                val titleElement = nodeElement.getElementsByTagName(TAGS.TITLE_TAG).item(0)
                val creatorElement = nodeElement.getElementsByTagName(TAGS.CREATOR_TAG).item(0)
                val enclosureElement = nodeElement.getElementsByTagName(TAGS.ENCLOSURE_TAG).item(0)
                val imageElement = nodeElement.getElementsByTagName(TAGS.ITUNES_IMAGE_TAG).item(0)
                val linkElement = nodeElement.getElementsByTagName(LINK_TAG).item(0)

                val title = if (titleElement != null && titleElement.hasChildNodes()) titleElement.firstChild.nodeValue else ""
                val pastor = if (creatorElement != null && creatorElement.hasChildNodes()) creatorElement.firstChild.nodeValue else ""
                val mp3 = enclosureElement.attributes.getNamedItem(TAGS.URL_TAG).nodeValue
                val cover = imageElement.attributes.getNamedItem(TAGS.HREF_TAG).nodeValue
                val link = linkElement.firstChild.nodeValue

                trackList.add(
                    PodcastTrackModel(
                        cover = cover,
                        title = title.replace(" - $pastor", ""),
                        pastor = pastor,
                        mp3URL = mp3,
                        link = link
                    )
                )
            }

            return PodcastModel(
                podcastCover = podcastCover,
                trackList = trackList
            )

        } catch (e: IOException) {
            e.printStackTrace()
        }

        /**
         * Return getTrackList() to retry fetching from url
         * Used as retry when connection is back
         * TODO : create network error handling
         * */
        return getTrackList()
    }

    object TAGS {
        const val IMAGE_TAG = "image"
        const val ITEM_TAG = "item"

        const val TITLE_TAG = "title"
        const val CREATOR_TAG = "dc:creator"
        const val ENCLOSURE_TAG = "enclosure"
        const val URL_TAG = "url"
        const val LINK_TAG = "link"
        const val ITUNES_IMAGE_TAG = "itunes:image"
        const val HREF_TAG = "href"
    }
}