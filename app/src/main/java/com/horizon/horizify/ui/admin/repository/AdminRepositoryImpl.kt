package com.horizon.horizify.ui.admin.repository

import com.horizon.horizify.repository.FirebaseRepository

class AdminRepositoryImpl(private val firebaseRepository: FirebaseRepository) : AdminRepository, FirebaseRepository by firebaseRepository {
}