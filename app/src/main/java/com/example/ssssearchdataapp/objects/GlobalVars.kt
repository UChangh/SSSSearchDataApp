package com.example.ssssearchdataapp.objects

import com.example.ssssearchdataapp.MainActivity
import com.example.ssssearchdataapp.externaldatas.models.images.Document
import java.time.format.DateTimeFormatter

object GlobalVars {
    var items = mutableListOf<Document>()
    var mainActivity: MainActivity? = null
    var favItems = mutableListOf<Document>()
    val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val emptyText = "Text is Empty"
}