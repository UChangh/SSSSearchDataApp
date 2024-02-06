package com.example.ssssearchdataapp.externaldatas.models.videos

data class VideoDataGroup(
    val documents: List<Document>,
    val meta: Meta
)

data class Document(
    val author: String,
    val datetime: String,
    val play_time: Int,
    val thumbnail: String,
    val title: String,
    val url: String
)

data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int
)