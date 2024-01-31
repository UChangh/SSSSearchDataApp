package com.example.ssssearchdataapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    var frag:ArrayList<Fragment> = ArrayList()
    override fun getItemCount() = frag.size

    override fun createFragment(position: Int): Fragment {
        return frag[position]
    }

    fun addFrag(f:Fragment) {
        frag.add(f)
        notifyItemInserted(frag.size - 1)
    }

    fun removeFrag() {
        frag.removeLast()
        notifyItemRemoved(frag.size)
    }
}