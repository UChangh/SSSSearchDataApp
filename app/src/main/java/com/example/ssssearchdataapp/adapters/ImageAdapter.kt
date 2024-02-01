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
import com.example.ssssearchdataapp.externaldatas.Document
import com.example.ssssearchdataapp.objects.GlobalVars.dtf
import com.example.ssssearchdataapp.objects.GlobalVars.mainActivity
import java.time.OffsetDateTime

//class ImageAdapter(private var dataset: List<Document>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    fun getItems(items : List<Document>){
//        dataset = items
//        notifyDataSetChanged()
//    }
//
//    var imageClick: ImageClick? = null
//
//    inner class Holder(binding: FragmentItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
//        val img = binding.imageView
//        val blog = binding.tvBlog
//        val time = binding.tvDateTime
//        val layout = binding.itemLayout
//        val like = binding.ivFavorite
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val binding = FragmentItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return Holder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val holder = holder as Holder
//        holder.layout.setOnClickListener {
//            imageClick?.isLike(it, position)
//        }
//        Glide.with(mainActivity!!)
//            .load(dataset[position].thumbnail_url)
//            .fallback(R.drawable.sample_iceland)
//            .fitCenter()
//            .into(holder.img)
//        holder.blog.text = dataset[position].display_sitename
//        holder.time.text = dtf.format(OffsetDateTime.parse(dataset[position].datetime))
//        when(dataset[position] in favItems) {
//            true -> {
//                holder.like.setImageResource(R.drawable.star)
//                holder.like.visibility = View.VISIBLE
//            }
//            false -> {
//                holder.like.setImageResource(R.drawable.start)
//                holder.like.visibility = View.GONE
//            }
//        }
//    }
//
//    override fun getItemCount() = dataset.size
//}

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