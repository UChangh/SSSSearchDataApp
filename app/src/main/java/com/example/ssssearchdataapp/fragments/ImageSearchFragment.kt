package com.example.ssssearchdataapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ssssearchdataapp.MainActivity
import com.example.ssssearchdataapp.databinding.FragmentImageSearchBinding
import com.example.ssssearchdataapp.externaldatas.Document


class ImageSearchFragment : Fragment() {
    private var _binding : FragmentImageSearchBinding? = null
    private val binding get() = _binding!!

//    private lateinit var mainActivity:MainActivity
    lateinit var adapter:ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("ISF","onViewCreated : $items")
        adapter = ImageAdapter(items)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(mainActivity, 2, GridLayoutManager.VERTICAL, false)
    }

    companion object {
        var items = mutableListOf<Document>()
        var mainActivity: MainActivity? = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}