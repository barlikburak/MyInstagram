package com.bb.nst.data.model.access_token.response

import com.google.gson.annotations.SerializedName

/**
 * Class created for 60 minute token.
 */
data class ShortLivedAccessTokenResponse(
    @SerializedName("user_id")
    val user_id: Long?,
    @SerializedName("access_token")
    val access_token: String?
)
