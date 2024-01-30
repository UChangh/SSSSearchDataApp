package com.example.ssssearchdataapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ssssearchdataapp.GlobalVars.favItems
import com.example.ssssearchdataapp.GlobalVars.mainActivity
import com.example.ssssearchdataapp.MainActivity
import com.example.ssssearchdataapp.databinding.FragmentImageLikeBinding

class ImageLikeFragment : Fragment() {
    private var _binding : FragmentImageLikeBinding? = null
    private val binding get() = _binding!!

    lateinit var likeAdapter:ImageLikeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity
        likeAdapter = ImageLikeAdapter(favItems)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        likeAdapter = ImageLikeAdapter(favItems)
        binding.recyclerViewLike.apply {
            this.adapter = likeAdapter
            this.layoutManager = GridLayoutManager(mainActivity, 2, GridLayoutManager.VERTICAL, false)
        }
        likeAdapter.favClick = object : FavoriteClick {
            override fun dislike(v: View, pos: Int) {
                favItems.remove(favItems[pos])
                likeAdapter.getFav(favItems)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}