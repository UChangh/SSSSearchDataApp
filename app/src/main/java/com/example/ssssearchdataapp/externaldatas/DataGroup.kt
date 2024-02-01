package com.example.ssssearchdataapp.externaldatas


data class DataGroup(
    // List -> MutableList
    val documents: MutableList<Document>,
    val meta: Meta
)

/**
 * 메소드 내용   // 이게 뭘까??
 *
 * @property display_sitename 사이트 제목
 * @property thumbnail_url 썸네일 주소
 * @property datetime 날짜(ISO형식)
 */
data class Document(
    val collection: String,
    val datetime: String,
    var display_sitename: String,
    val doc_url: String,
    val height: Int,
    val image_url: String,
    val thumbnail_url: String,
    val width: Int
)

data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int
)