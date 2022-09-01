package com.horizon.horizify.ui.admin.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.commonModel.BannerModel
import com.horizon.horizify.commonModel.CarouselModel
import com.horizon.horizify.commonModel.ImageUriModel
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.ui.admin.repository.AdminRepository
import com.horizon.horizify.ui.home.model.HeaderCardModel
import com.horizon.horizify.utils.Constants.PRIMARY
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class AdminViewModel(val context: Context, val repository: AdminRepository) : BaseViewModel(), IAdminViewModel {

    override val imageUriModel = MutableStateFlow(ImageUriModel())
    override val primaryBanner = MutableStateFlow(BannerModel())
    override val carousel = MutableStateFlow(CarouselModel())
    override val pageState = MutableStateFlow<PageState>(PageState.Completed)

    override fun getBannerCarousels(): List<HeaderCardModel> {
        val cardList = arrayListOf<HeaderCardModel>()
        cardList.add(HeaderCardModel(isDefault = true))
        return cardList
    }

    override fun setNewImageUri(uri: Uri) {
        imageUriModel.update { ImageUriModel(uri) }
    }

    override fun saveBanner(type: AdminSetupEnum, banner: BannerModel) {
        pageState.value = PageState.Loading
        val file = imageUriModel.value
        if (file != null) {
            val storageReference = repository.getStorageReference(type)
            storageReference.putFile(file.uri!!).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    val newBanner = banner.copy(image = it.toString(), date = repository.getCurrentDateTime())
                    saveBannerToDatabase(type, newBanner)
                }.addOnFailureListener {
                    pageState.value = PageState.Error
                    Toast.makeText(context, "download Image Url : Failed", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                pageState.value = PageState.Error
                Toast.makeText(context, "uploadImageToFirebase : Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun saveBannerToDatabase(type: AdminSetupEnum, banner: BannerModel) {

        val postId = when (type) {
            AdminSetupEnum.PRIMARY_BANNER -> PRIMARY
//            else -> FirebaseAuth.getInstance().uid ?: ""
            else -> ""
        }

        val databaseReference = repository.getDatabaseReference(type)
        databaseReference.child(postId).setValue(banner).addOnSuccessListener {
            pageState.value = PageState.Completed
            Toast.makeText(context, "Firebase save to database : Success", Toast.LENGTH_SHORT)
        }.addOnFailureListener {
            pageState.value = PageState.Error
        }
    }

    override fun loadBanners() {
        getPrimaryBanner()
        getCarousel()
    }

    override fun getPrimaryBanner() {
        pageState.value = PageState.Loading
        val databaseReference = repository.getDatabaseReference(AdminSetupEnum.PRIMARY_BANNER)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val child = snapshot.child(PRIMARY)
                val banner = child.getValue(BannerModel::class.java)
                if (banner != null) primaryBanner.update { banner }
                pageState.value = PageState.Completed
            }

            override fun onCancelled(error: DatabaseError) {
                pageState.value = PageState.Completed
            }

        })

    }

    override fun getCarousel() {
        val databaseReference = repository.getDatabaseReference(AdminSetupEnum.BANNER)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val banner = it.getValue(BannerModel::class.java)
                    if (banner != null) carousel.value.banners.add(banner)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}