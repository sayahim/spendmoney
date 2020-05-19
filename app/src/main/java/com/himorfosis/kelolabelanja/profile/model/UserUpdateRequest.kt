package com.himorfosis.kelolabelanja.profile.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.PartMap

class UserUpdateRequest (
        var body: MultipartBody.Part,
        var map: Map<String, RequestBody>
)
