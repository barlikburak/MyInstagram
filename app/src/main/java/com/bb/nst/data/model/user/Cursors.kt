package com.bb.nst.data.model.user

import com.google.gson.annotations.SerializedName

/**
 * It is used in the object inside the paging class for the media list.
 */
data class Cursors (
    @SerializedName("after")
    val after: String,
    @SerializedName("before")
    val before: String
)