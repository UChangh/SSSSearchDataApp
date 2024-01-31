package com.example.ssssearchdataapp.adapters

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ssssearchdataapp.fragments.ImageLikeFragment
import com.example.ssssearchdataapp.fragments.ImageSearchFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when(position) {
        1 -> ImageLikeFragment()
        else -> ImageSearchFragment()
    }
}