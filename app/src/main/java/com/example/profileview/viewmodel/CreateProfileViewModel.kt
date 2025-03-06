package com.example.profileview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profileview.data.ProfileRepository
import com.example.profileview.model.Profile
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

@HiltViewModel
class CreateProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModel() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles: Flow<List<Profile>> = _profiles.asStateFlow()

    init {
        firebaseAnalytics = Firebase.analytics
        getAllProfiles()
    }

    fun insertProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.insertProfile(profile)
            logProfileCreatedEvent()
            getAllProfiles()
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.updateProfile(profile)
            logProfileEditedEvent()
            getAllProfiles()
        }
    }

    fun deleteProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.deleteProfile(profile)
            logProfileDeletedEvent()
            getAllProfiles()
        }
    }

    private fun getAllProfiles() {
        viewModelScope.launch {
            profileRepository.getAllProfiles().collectLatest { profiles ->
                _profiles.update { profiles }
            }
        }
    }

    private fun logProfileCreatedEvent() {
        firebaseAnalytics.logEvent("profile_created") {
            param("event_type", "profile_created")
        }
    }

    private fun logProfileEditedEvent() {
        firebaseAnalytics.logEvent("profile_edited") {
            param("event_type", "profile_edited")
        }
    }

    private fun logProfileDeletedEvent() {
        firebaseAnalytics.logEvent("profile_deleted") {
            param("event_type", "profile_deleted")
        }
    }
}