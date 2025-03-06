package com.example.profileview

import android.content.Context
import com.example.profileview.data.ProfileRepository
import com.example.profileview.data.local.ProfileDao
import com.example.profileview.data.local.ProfileDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideProfileDatabase(@ApplicationContext context: Context): ProfileDatabase {
        return ProfileDatabase.getDatabase(context)
    }

    @Provides
    fun provideProfileDao(profileDatabase: ProfileDatabase): ProfileDao {
        return profileDatabase.profileDao()
    }

    @Provides
    fun provideProfileRepository(profileDao: ProfileDao): ProfileRepository {
        return ProfileRepository(profileDao)
    }
}