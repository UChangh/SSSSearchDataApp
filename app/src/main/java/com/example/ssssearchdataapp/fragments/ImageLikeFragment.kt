package com.example.ssssearchdataapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ssssearchdataapp.MainActivity
import com.example.ssssearchdataapp.databinding.FragmentImageLikeBinding
import com.example.ssssearchdataapp.interfaces.FavoriteClick
import com.example.ssssearchdataapp.objects.GlobalVars.favItems
import com.example.ssssearchdataapp.objects.GlobalVars.mainActivity

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
            this.itemAnimator = null
        }
        likeAdapter.favClick = object : FavoriteClick {     // 보관함에서 아이템 클릭하는 경우
            override fun dislike(v: View, pos: Int) {
                favItems.remove(favItems[pos])              // pos위치에서 아이템 삭제
                likeAdapter.getFav(favItems)                // 삭제 후 adapter에 데이터 전달 & 업데이트
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}