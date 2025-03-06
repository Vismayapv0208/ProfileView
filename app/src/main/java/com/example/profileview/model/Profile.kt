package com.example.profileview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    var id: Int = 0,
    var name: String,
    var profession: String,
    var address: String,
    var isCompleted: Boolean = false
) : Parcelable