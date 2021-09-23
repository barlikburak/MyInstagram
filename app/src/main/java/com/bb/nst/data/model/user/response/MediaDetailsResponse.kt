package com.bb.nst.data.model.user.response

import com.google.gson.annotations.SerializedName

/**
 * A class created for media details.
 */
data class MediaDetailsResponse (
    @SerializedName("id")
    val id: String,
    @SerializedName("media_type")
    val media_type: String,
    @SerializedName("media_url")
    val media_url: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("timestamp")
    val timestamp: String
)