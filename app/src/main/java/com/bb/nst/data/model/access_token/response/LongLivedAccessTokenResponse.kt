package com.bb.nst.data.model.access_token.response

import com.google.gson.annotations.SerializedName

/**
 * Class created for 60 day token.
 */
data class LongLivedAccessTokenResponse(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("token_type")
    val token_type: String,
    @SerializedName("expires_in")
    val expires_in: Long
)
