package com.example.ssssearchdataapp.objects

import com.example.ssssearchdataapp.MainActivity
import com.example.ssssearchdataapp.externaldatas.Document

object GlobalVars {
    var items = mutableListOf<Document>()
    var mainActivity: MainActivity? = null
    var favItems = mutableListOf<Document>()
}