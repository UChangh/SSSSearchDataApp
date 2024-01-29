package com.example.ssssearchdataapp.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssssearchdataapp.R
import com.example.ssssearchdataapp.databinding.FragmentItemRecyclerBinding
import com.example.ssssearchdataapp.externaldatas.Document
import com.example.ssssearchdataapp.fragments.ImageSearchFragment.Companion.mainActivity
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ImageAdapter(private var dataset: List<Document>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun getItems(items : List<Document>){
        dataset = items
        notifyDataSetChanged()
    }

    inner class Holder(binding: FragmentItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        val img = binding.imageView
        val blog = binding.textView2
        val time = binding.textView3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FragmentItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder = holder as Holder
        Glide.with(mainActivity!!)
            .load(dataset[position].thumbnail_url)
            .fallback(R.drawable.sample_iceland)
            .fitCenter()
            .into(holder.img)
        holder.blog.text = dataset[position].display_sitename
        holder.time.text = dtf.format(OffsetDateTime.parse(dataset[position].datetime))
    }
    val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun getItemCount() = dataset.size
}