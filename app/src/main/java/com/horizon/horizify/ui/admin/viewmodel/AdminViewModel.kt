package com.horizon.horizify.ui.admin.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.common.model.BannerModel
import com.horizon.horizify.common.model.BannerTypeModel
import com.horizon.horizify.common.model.ImageUriModel
import com.horizon.horizify.common.model.MainBannerModel
import com.horizon.horizify.extensions.toFormattedDate
import com.horizon.horizify.extensions.trimAllSpaces
import com.horizon.horizify.ui.admin.AdminModule.SETUP_BANNER_MODEL
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.ui.admin.repository.AdminRepository
import com.horizon.horizify.utils.Constants.BANNER
import com.horizon.horizify.utils.Constants.PRIMARY
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.format.DateTimeFormatter

class AdminViewModel(val context: Context, val repository: AdminRepository) : BaseViewModel(), IAdminViewModel {

    override val imageUriModel = MutableStateFlow(ImageUriModel())
    override val bannerTypeModel = MutableStateFlow(BannerTypeModel())
    override val bannerCarousel = MutableStateFlow(MainBannerModel())
    override val pageState = MutableStateFlow<PageState>(PageState.Completed)
    override val saveState = MutableStateFlow<PageState>(PageState.Error)

    override fun saveBanner(model: BannerTypeModel) {
        repository.saveBanner(SETUP_BANNER_MODEL, model)
    }

    override fun setupBanner(): BannerTypeModel {
        val bannerTypeModel = repository.getBanner(SETUP_BANNER_MODEL)
        this@AdminViewModel.bannerTypeModel.value = bannerTypeModel
        return bannerTypeModel
    }

    override fun setNewImageUri(uri: Uri) {
        imageUriModel.update { ImageUriModel(uri) }
    }

    override fun save(type: AdminSetupEnum, banner: BannerModel) {
        saveState.value = PageState.Loading
        pageState.value = PageState.Loading

        val file = imageUriModel.value
        if (file.uri != null) {
            val storageReference = repository.getStorageReference(type)

            storageReference.putFile(file.uri!!).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    val newBanner = banner.copy(image = it.toString())
                    // save banner details after saving image to storage
                    saveBannerToDatabase(type, newBanner)
                }.addOnFailureListener {
                    saveState.value = PageState.Error
                    pageState.value = PageState.Error
                    Toast.makeText(context, "Failed to fetch uploaded image.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                saveState.value = PageState.Error
                pageState.value = PageState.Error
                Toast.makeText(context, "Failed to upload banner.", Toast.LENGTH_SHORT).show()
            }
        } else {
            // save without update on image
            saveBannerToDatabase(type, banner)
        }
    }

    override fun saveBannerToDatabase(type: AdminSetupEnum, banner: BannerModel) {
        val postId = when (type) {
            AdminSetupEnum.PRIMARY_BANNER -> PRIMARY
            else -> {
                val trimmedTitle = banner.title.trimAllSpaces()
                "$BANNER$trimmedTitle"
            }
        }
        val databaseReference = repository.getDatabaseReference(type)
        databaseReference.child(postId).setValue(banner).addOnSuccessListener {
            saveState.value = PageState.Completed
            pageState.value = PageState.Completed
            Toast.makeText(context, "Firebase save to database : Success", Toast.LENGTH_SHORT)
        }.addOnFailureListener {
            saveState.value = PageState.Error
            pageState.value = PageState.Error
        }
    }

    override fun getBannerCarousel() {
        getPrimaryBanner()
        getBanners()
    }

    override fun getPrimaryBanner() {
        pageState.value = PageState.Loading
        val databaseReference = repository.getDatabaseReference(AdminSetupEnum.PRIMARY_BANNER)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val child = snapshot.child(PRIMARY)
                val banner = child.getValue(BannerModel::class.java)
                if (banner != null) bannerCarousel.value = bannerCarousel.value.copy(primary = banner)
                pageState.value = PageState.Completed
            }

            override fun onCancelled(error: DatabaseError) {
                pageState.value = PageState.Error
                Toast.makeText(context, "Unable to get primary banner.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getBanners() {
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
                    bannerCarousel.value = bannerCarousel.value.copy(banners = ArrayList(bannerListSorted))
                }
                pageState.value = PageState.Completed
            }

            override fun onCancelled(error: DatabaseError) {
                pageState.value = PageState.Error
                Toast.makeText(context, "Unable to get banners.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun updateBanner(type: AdminSetupEnum, banner: BannerModel, postId: String) {
        removeBanner(type, banner, postId)
        save(type, banner)
    }

    override fun removeBanner(type: AdminSetupEnum, banner: BannerModel, postId: String) {
        pageState.value = PageState.Loading

        val postId = when (type) {
            AdminSetupEnum.PRIMARY_BANNER -> PRIMARY
            else -> {
                "$BANNER$postId"
            }
        }

        val databaseReference = repository.getDatabaseReference(type)
        databaseReference.child(postId).removeValue().addOnSuccessListener {
            saveState.value = PageState.Completed
            pageState.value = PageState.Completed
        }.addOnFailureListener {
            saveState.value = PageState.Error
            pageState.value = PageState.Error
        }
    }

    override fun getCurrentDateTime(): String {
        val dateTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        return dateTime.toFormattedDate("yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss")
    }
}