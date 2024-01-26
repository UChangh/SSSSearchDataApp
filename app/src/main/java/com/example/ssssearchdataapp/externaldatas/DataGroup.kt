package com.example.ssssearchdataapp.externaldatas

import com.google.gson.annotations.SerializedName

data class DataGroup(
    val documents: List<Document>,
    val meta: Meta
)

data class Document(
    val datetime: String,
    @SerializedName("display_sitename")
    val siteName: String,
    @SerializedName("thumbnail_url")
    val thumbnail: String,
)

data class Meta(
    @SerializedName("is_end")
    val end: Boolean,
    @SerializedName("pageable_count")
    val page: Int,
    @SerializedName("total_count")
    val total: Int
)