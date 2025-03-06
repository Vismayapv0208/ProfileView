package com.example.profileview.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Update
    suspend fun updateProfile(profile: ProfileEntity)

    @Delete
    suspend fun deleteProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profile_table")
    fun getAllProfiles(): Flow<List<ProfileEntity>>

    @Query("SELECT * FROM profile_table WHERE id = :profileId")
    fun getProfileById(profileId: Int): Flow<ProfileEntity>
}