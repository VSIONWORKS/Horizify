package com.horizon.horizify.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.horizon.horizify.extensions.toFormattedDate
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.utils.Constants.BANNER_DATABASE
import com.horizon.horizify.utils.Constants.BANNER_STORAGE
import com.horizon.horizify.utils.Constants.NETWORK_DATABASE
import com.horizon.horizify.utils.Constants.NETWORK_STORAGE
import com.horizon.horizify.utils.Constants.PRIMARY_BANNER_DATABASE
import com.horizon.horizify.utils.Constants.PRIMARY_BANNER_STORAGE
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class FirebaseRepositoryImpl : FirebaseRepository {

    override fun getStorageReference(type: AdminSetupEnum): StorageReference {
        val filename = UUID.randomUUID().toString()

        val storageRef = when (type) {
            AdminSetupEnum.PRIMARY_BANNER -> PRIMARY_BANNER_STORAGE
            AdminSetupEnum.PRIMARY_BANNER -> BANNER_STORAGE
            else -> NETWORK_STORAGE
        }

        return FirebaseStorage.getInstance().getReference(storageRef + filename)
    }

    override fun getDatabaseReference(type: AdminSetupEnum): DatabaseReference {
        val databaseRef = when (type) {
            AdminSetupEnum.PRIMARY_BANNER -> PRIMARY_BANNER_DATABASE
            AdminSetupEnum.PRIMARY_BANNER -> BANNER_DATABASE
            else -> NETWORK_DATABASE
        }
        return FirebaseDatabase.getInstance().getReference(databaseRef)
    }

    override fun getImageFromStorage() {

    }

    override fun getDataFromDatabase() {

    }

    override fun getCurrentDateTime(): String {
        val dateTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        return dateTime.toFormattedDate("yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss")
    }
}