package com.bb.nst.data.model.user.response

import com.google.gson.annotations.SerializedName

/**
 * Class we created to get username and id.
 */
data class UserInfoResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String
)
