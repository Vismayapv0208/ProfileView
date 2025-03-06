package com.example.profileview.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "profession") val profession: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean = false
)