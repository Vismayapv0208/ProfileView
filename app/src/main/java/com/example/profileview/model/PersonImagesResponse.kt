package com.example.profileview.model

data class PersonImagesResponse(
    val profiles: List<ProfileImage>
)

data class ProfileImage(
    val file_path: String,
    val width: Int,
    val height: Int
)
