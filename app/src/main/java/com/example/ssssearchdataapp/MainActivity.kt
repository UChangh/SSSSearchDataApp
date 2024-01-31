package com.example.ssssearchdataapp

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.ssssearchdataapp.adapters.ImageAdapter
import com.example.ssssearchdataapp.adapters.ViewPagerAdapter
import com.example.ssssearchdataapp.databinding.ActivityMainBinding
import com.example.ssssearchdataapp.externaldatas.DataRequestURLs
import com.example.ssssearchdataapp.fragments.ImageLikeFragment
import com.example.ssssearchdataapp.fragments.ImageSearchFragment
import com.example.ssssearchdataapp.objects.GlobalVars.items
import com.example.ssssearchdataapp.objects.KakaoAPIKey
import com.example.ssssearchdataapp.objects.SharedPreferenceKey.PREF_DEFAULT_VALUE
import com.example.ssssearchdataapp.objects.SharedPreferenceKey.PREF_KEY
import com.example.ssssearchdataapp.objects.SharedPreferenceKey.RECENT_KEY
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var fragmentSearch:ImageSearchFragment

    private lateinit var viewPager:ViewPager2
    private lateinit var tabLayout:TabLayout

    private lateinit var search:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val main = binding.root
        setContentView(main)

        viewPagerInit()

        fragmentSearch = ImageSearchFragment()
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        binding.apply {
            btnSearch.setOnClickListener {
                search = binding.etSearchBar.text.toString()
                getData(search, 80)
//                fragmentSearch.recyclerView.scrollToPosition(0) // 검색결과를 맨 위에서부터 보여주기
                saveHistory(search)
            }
        }
        loadHistory()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    // ViewPager & Tablayout
    private fun viewPagerInit() {
        val vpa = ViewPagerAdapter(this)
        vpa.addFrag(ImageSearchFragment())
        vpa.addFrag(ImageLikeFragment())

        binding.viewPager.apply {
            adapter = vpa
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {   // ?
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//                }
            })     // ?
        }

        TabLayoutMediator(binding.tabLayout,binding.viewPager) { tab, position ->
            Log.e("YMC", "ViewPager position: $position")
            when(position) {
                0 -> tab.text = "이미지 검색"
                1 -> tab.text = "내 보관함"
            }
        }.attach()
    }

    // EditText 터치 시 포커스 획득 / 외부화면 터치 시 포커스 해제
    fun getFocused(v: View) {
        when(v) {
            binding.etSearchBar -> currentFocus
            else -> currentFocus?.clearFocus()
        }
    }

    // 외부 화면 터치 시 키보드 숨기기(Manifest에 키보드 기본설정 필수(windowSoftInputMode))
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm:InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    // 뒤로가기 버튼 클릭 시 종료 다이얼로그 띄우기
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val dialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("종료")
                .setMessage("종료하시겠습니까?")

            dialog.setPositiveButton("확인") { _, _ ->
                if(!isFinishing) finish()
            }
            dialog.setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    // 받아온 Data를 items라는 Document 타입 MutableList에 저장
    private fun getData(query: String, size: Int) = lifecycleScope.launch {
        val response = DataRequestURLs.kakaoNetwork.getItem(KakaoAPIKey.REST_API_KEY, query, size)
        Log.d("Parsing Test ::", response.toString())
        items = response.documents
        fragmentSearch.imageAdapter = ImageAdapter(items)
        fragmentSearch.imageAdapter.getItems(items)
    }

    // History 저장
    private fun saveHistory(s:String) {
        getSharedPreferences(PREF_KEY, MODE_PRIVATE)
            .edit()
            .putString(RECENT_KEY,s)
            .apply()
    }

    // History 불러오기
    private fun loadHistory() {
        val getSharedPref = getSharedPreferences(PREF_KEY, MODE_PRIVATE)
        binding.etSearchBar.setText(getSharedPref.getString(RECENT_KEY, PREF_DEFAULT_VALUE))
    }
}