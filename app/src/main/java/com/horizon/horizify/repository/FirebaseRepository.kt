package com.horizon.horizify.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.horizon.horizify.ui.admin.AdminSetupEnum

interface FirebaseRepository {

    fun getStorageReference(type: AdminSetupEnum): StorageReference
    fun getDatabaseReference(type: AdminSetupEnum): DatabaseReference

    fun getImageFromStorage()
    fun getDataFromDatabase()
}