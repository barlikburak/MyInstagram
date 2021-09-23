package com.bb.nst.data.model.user.response

import com.bb.nst.data.model.user.Id
import com.bb.nst.data.model.user.Paging
import com.google.gson.annotations.SerializedName

/**
 * We will use this class by generating 2 different objects as a post list and an album.
 */
data class MediaListResponse(
    @SerializedName("data")
    val data: List<Id>,
    @SerializedName("paging")
    val paging: Paging
)
