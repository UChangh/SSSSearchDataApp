package com.example.ssssearchdataapp.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssssearchdataapp.GlobalVars.favItems
import com.example.ssssearchdataapp.GlobalVars.mainActivity
import com.example.ssssearchdataapp.R
import com.example.ssssearchdataapp.databinding.FragmentItemRecyclerBinding
import com.example.ssssearchdataapp.externaldatas.Document
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ImageAdapter(private var dataset: List<Document>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun getItems(items : List<Document>){
        dataset = items
        notifyDataSetChanged()
    }

    var imageClick:ImageClick? = null

    inner class Holder(binding: FragmentItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        val img = binding.imageView
        val blog = binding.tvBlog
        val time = binding.tvDateTime
        val layout = binding.itemLayout
        val like = binding.ivFavorite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FragmentItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder = holder as Holder
        holder.layout.setOnClickListener {
            imageClick?.isLike(it, position)
        }
        Glide.with(mainActivity!!)
            .load(dataset[position].thumbnail_url)
            .fallback(R.drawable.sample_iceland)
            .fitCenter()
            .into(holder.img)
        holder.blog.text = dataset[position].display_sitename
        holder.time.text = dtf.format(OffsetDateTime.parse(dataset[position].datetime))
        when(dataset[position] in favItems) {
            true -> {
                holder.like.setImageResource(R.drawable.star)
                holder.like.visibility = View.VISIBLE
            }
            false -> {
                holder.like.setImageResource(R.drawable.start)
                holder.like.visibility = View.GONE
            }
        }
    }
    val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun getItemCount() = dataset.size
}