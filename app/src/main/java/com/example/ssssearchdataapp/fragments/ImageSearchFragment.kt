package com.example.ssssearchdataapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ssssearchdataapp.MainActivity
import com.example.ssssearchdataapp.adapters.ImageAdapter
import com.example.ssssearchdataapp.adapters.ImageLikeAdapter
import com.example.ssssearchdataapp.databinding.FragmentImageSearchBinding
import com.example.ssssearchdataapp.objects.GlobalVars.favItems
import com.example.ssssearchdataapp.objects.GlobalVars.mainActivity


class ImageSearchFragment : Fragment() {
    private lateinit var binding : FragmentImageSearchBinding

    lateinit var imageAdapter: ImageAdapter

    private lateinit var ila: ImageLikeAdapter

    private lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerViewSearch
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageAdapter = ImageAdapter()
        ila = ImageLikeAdapter(favItems)
        binding.recyclerViewSearch.apply {
            this.adapter = imageAdapter
            this.layoutManager = GridLayoutManager(mainActivity, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}