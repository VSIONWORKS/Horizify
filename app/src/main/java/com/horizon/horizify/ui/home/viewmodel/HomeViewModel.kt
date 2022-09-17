package com.horizon.horizify.ui.home.viewmodel

import android.content.Context
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.horizon.horizify.R
import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.base.SharedPreference
import com.horizon.horizify.common.model.BannerModel
import com.horizon.horizify.common.model.MainBannerModel
import com.horizon.horizify.extensions.save
import com.horizon.horizify.repository.FirebaseRepository
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.ui.home.model.HeaderCardModel
import com.horizon.horizify.utils.Constants
import com.horizon.horizify.utils.Constants.WEB_URL
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel(private val context: Context, private val repository: FirebaseRepository) : BaseViewModel(), IHomeViewModel {

    private val prefs = SharedPreference(context)

    override val selectedBanner = MutableStateFlow(BannerModel())
    override val bannerCarousel = MutableStateFlow(MainBannerModel())
    override val pageState = MutableStateFlow<PageState>(PageState.Completed)

    init {
        getPrimaryBanner()
    }

    override fun setSelectedBanner(bannerModel: BannerModel) {
        selectedBanner.value = bannerModel
    }

    override fun getPrimaryBanner() {
        pageState.value = PageState.Loading
        val databaseReference = repository.getDatabaseReference(AdminSetupEnum.PRIMARY_BANNER)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val child = snapshot.child(Constants.PRIMARY)
                val banner = child.getValue(BannerModel::class.java)
                if (banner != null) getBannerCarousel(banner)
            }

            override fun onCancelled(error: DatabaseError) {
                pageState.value = PageState.Error
                Toast.makeText(context, "Unable to get primary banner.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getBannerCarousel(bannerModel: BannerModel) {
        val databaseReference = repository.getDatabaseReference(AdminSetupEnum.BANNER)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var bannerList: ArrayList<BannerModel> = arrayListOf()
                snapshot.children.forEach {

                    val banner = it.getValue(BannerModel::class.java)
                    if (banner != null) {
                        bannerList.add(banner)
                    }
                }

                if (bannerList.size > 0) {
                    val bannerListSorted = bannerList.sortedWith(compareBy { it.date })
                    bannerCarousel.value = bannerCarousel.value.copy(primary = bannerModel, banners = ArrayList(bannerListSorted))
                } else {
                    bannerCarousel.value = bannerCarousel.value.copy(primary = bannerModel)
                }
                pageState.value = PageState.Completed
            }

            override fun onCancelled(error: DatabaseError) {
                pageState.value = PageState.Error
                Toast.makeText(context, "Unable to get banners.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getHeaderCardImages(): List<HeaderCardModel> {
        // temporary implementation for local fetching
        val cardList = arrayListOf<HeaderCardModel>()
        cardList.add(HeaderCardModel(drawableId = R.drawable.header))
        cardList.add(HeaderCardModel(drawableId = R.drawable.g12))
        cardList.add(HeaderCardModel(drawableId = R.drawable.hoorayzone))
        return cardList
    }

    override fun saveWebUrl(url: String) {
        prefs.removeValue(WEB_URL)
        prefs.save(WEB_URL, url)
    }
}