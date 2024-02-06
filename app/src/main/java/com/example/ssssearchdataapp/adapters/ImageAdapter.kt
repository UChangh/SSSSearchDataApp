package com.example.ssssearchdataapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssssearchdataapp.R
import com.example.ssssearchdataapp.externaldatas.models.images.Document
import com.example.ssssearchdataapp.objects.GlobalVars.dtf
import com.example.ssssearchdataapp.objects.GlobalVars.mainActivity
import java.time.OffsetDateTime

class ImageAdapter: ListAdapter<Document, ImageAdapter.Holder>(diff) {
    fun getItems(items : List<Document>){

    }

    inner class Holder(val v: View): RecyclerView.ViewHolder(v){
        fun bind(docs: Document){
            val image = v.findViewById<ImageView>(R.id.imageView)
            val blog = v.findViewById<TextView>(R.id.tvBlog)
            val date = v.findViewById<TextView>(R.id.tvDateTime)

            Glide.with(mainActivity!!)
                .load(docs.thumbnail_url)
                .fallback(R.drawable.sample_iceland)
                .fitCenter()
                .into(image)
            blog.text = docs.display_sitename
            date.text = dtf.format(OffsetDateTime.parse(docs.datetime))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.Holder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.fragment_image_search, parent, false)
        return Holder(inflate)
    }

    override fun onBindViewHolder(holder: ImageAdapter.Holder, position: Int) {
        holder.bind(currentList[position])
    }
    companion object{
        val diff = object: DiffUtil.ItemCallback<Document>(){
            override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
                return oldItem.doc_url == newItem.doc_url
            }

            override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
                return oldItem == newItem
            }
        }
    }
}