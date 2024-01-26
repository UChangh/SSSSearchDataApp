package com.example.ssssearchdataapp.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ssssearchdataapp.R
import com.example.ssssearchdataapp.databinding.FragmentItemRecyclerBinding
import com.example.ssssearchdataapp.externaldatas.Document

class ImageAdapter(private val dataset: List<Document>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class Holder(binding: FragmentItemRecyclerBinding):RecyclerView.ViewHolder(binding.root) {
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
        Log.i("This is ImageAdapter","${dataset.size}")
        holder.img.setImageResource(R.drawable.sample_iceland)
//        holder.img.setImageURI(Uri.parse(dataset[position].thumbnail_url))
        holder.blog.text = dataset[position].display_sitename
        holder.time.text = dataset[position].datetime
    }

    override fun getItemCount() = dataset.size
}