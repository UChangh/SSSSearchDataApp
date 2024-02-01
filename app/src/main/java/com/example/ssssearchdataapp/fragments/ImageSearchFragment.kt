package com.example.ssssearchdataapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ssssearchdataapp.MainActivity
import com.example.ssssearchdataapp.databinding.FragmentImageSearchBinding
import com.example.ssssearchdataapp.interfaces.ImageClick
import com.example.ssssearchdataapp.objects.GlobalVars.favItems
import com.example.ssssearchdataapp.objects.GlobalVars.items
import com.example.ssssearchdataapp.objects.GlobalVars.mainActivity


class ImageSearchFragment : Fragment() {
    private var _binding : FragmentImageSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var imageAdapter:ImageAdapter

    private lateinit var ila:ImageLikeAdapter

    val recyclerView by lazy { binding.recyclerViewSearch }

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
        imageAdapter = ImageAdapter(items)
        ila = ImageLikeAdapter(favItems)
        binding.recyclerViewSearch.apply {
            this.adapter = imageAdapter
            this.layoutManager = GridLayoutManager(mainActivity, 2, GridLayoutManager.VERTICAL, false)
            this.itemAnimator = null    // 아이템 변경 시 화면의 깜빡임을 방지
        }
        // Item 클릭 시 이벤트 처리
        imageAdapter.imageClick = object : ImageClick {
            override fun isLike(v: View, pos: Int) {
                when(items[pos]) {
                    !in favItems -> {   // 선택한 아이템이 보관함에 없을 경우
                        favItems.add(items[pos])
                        ila.getFav(favItems)
                    }
                    else -> {
                        favItems.remove(items[pos])
                        ila.getFav(favItems)
                    }
                }
                imageAdapter.notifyItemChanged(pos)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}