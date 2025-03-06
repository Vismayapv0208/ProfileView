package com.example.profileview.data

import com.example.profileview.data.local.ProfileDao
import com.example.profileview.data.local.ProfileEntity
import com.example.profileview.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    fun getAllProfiles(): Flow<List<Profile>> {
        return profileDao.getAllProfiles().map { profileEntities ->
            profileEntities.map { it.toProfile() }
        }
    }

    suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile.toProfileEntity())
    }

    suspend fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile.toProfileEntity())
    }

    suspend fun deleteProfile(profile: Profile) {
        profileDao.deleteProfile(profile.toProfileEntity())
    }

    private fun ProfileEntity.toProfile(): Profile {
        return Profile(
            id = id,
            name = name,
            profession = profession,
            address = address,
            isCompleted = isCompleted
        )
    }

    private fun Profile.toProfileEntity(): ProfileEntity {
        return ProfileEntity(
            id = id,
            name = name,
            profession = profession,
            address = address,
            isCompleted = isCompleted
        )
    }
}