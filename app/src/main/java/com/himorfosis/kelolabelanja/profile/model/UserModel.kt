package com.himorfosis.kelolabelanja.profile.model

class UserModel (
        var id: String,
        val name: String,
        val email: String,
        val phone_number: String,
        val image: String,
        val image_url: String,
        val gender: String,
        val born: String,
        val token: String,
        val active: Int,
        val created_at: String,
        val updated_at: String
)