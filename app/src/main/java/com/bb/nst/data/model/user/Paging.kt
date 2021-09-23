package com.bb.nst.data.model.user

import com.google.gson.annotations.SerializedName

/**
 * A class created to create an object within the MediaListResponse class.
 */
data class Paging(
    @SerializedName("cursors")
    val cursors: Cursors,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("next")
    val next: String
)
