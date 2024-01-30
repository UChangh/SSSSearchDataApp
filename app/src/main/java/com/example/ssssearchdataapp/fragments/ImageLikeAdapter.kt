package com.example.ssssearchdataapp.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssssearchdataapp.GlobalVars.mainActivity
import com.example.ssssearchdataapp.R
import com.example.ssssearchdataapp.databinding.FragmentItemRecyclerBinding
import com.example.ssssearchdataapp.externaldatas.Document
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ImageLikeAdapter(private var dataset: List<Document>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun getFav(items : List<Document>){ // dataset에 items를 덮어씌워 호출 시 마다 dataset을 갱신함
        dataset = items
        notifyDataSetChanged()
    }

    var favClick:FavoriteClick? = null

    inner class Holder(binding: FragmentItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        val img = binding.imageView
        val blog = binding.tvBlog
        val time = binding.tvDateTime
        val layout = binding.itemLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FragmentItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder = holder as Holder
        holder.layout.setOnClickListener {  // Item Layout이 선택 될 경우
            favClick?.dislike(it, position)
        }
        Glide.with(mainActivity!!)
            .load(dataset[position].thumbnail_url)
            .fallback(R.drawable.sample_iceland)    // thumbnail의 호출이 실패했을 경우
            .fitCenter()
            .into(holder.img)
        holder.blog.text = dataset[position].display_sitename
        holder.time.text = dtf.format(OffsetDateTime.parse(dataset[position].datetime))
    }
    val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun getItemCount() = dataset.size
}