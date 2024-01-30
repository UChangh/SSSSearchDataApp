package com.example.ssssearchdataapp

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.ssssearchdataapp.databinding.ActivityMainBinding
import com.example.ssssearchdataapp.externaldatas.DataRequestURLs
import com.example.ssssearchdataapp.fragments.ImageLikeFragment
import com.example.ssssearchdataapp.fragments.ImageSearchFragment
import com.example.ssssearchdataapp.objects.GlobalVars.items
import com.example.ssssearchdataapp.objects.KakaoAPIKey
import com.example.ssssearchdataapp.objects.SharedPreferenceKey.PREF_DEFAULT_VALUE
import com.example.ssssearchdataapp.objects.SharedPreferenceKey.PREF_KEY
import com.example.ssssearchdataapp.objects.SharedPreferenceKey.RECENT_KEY
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var fragmentSearch:ImageSearchFragment
    private lateinit var fragmentLike:ImageLikeFragment

    private lateinit var search:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val main = binding.root
        setContentView(main)

        fragmentSearch = ImageSearchFragment()
        fragmentLike = ImageLikeFragment()
        setFragment(fragmentSearch)
        binding.btnSearchImage.isEnabled = false

        binding.apply {
            // 이미지 검색 버튼을 눌렀을 경우
            btnSearchImage.setOnClickListener {
                fragmentSearch = ImageSearchFragment()
                setFragment(fragmentSearch)

                btnSearchImage.isEnabled = false
                btnFavorite.isEnabled = true
                // 내 보관함에 있을 시 검색버튼 활성화
                btnSearch.isEnabled = true
            }

            // 내 보관함 버튼을 눌렀을 경우
            btnFavorite.setOnClickListener {
                fragmentLike = ImageLikeFragment()
                setFragment(fragmentLike)

                btnSearchImage.isEnabled = true
                btnFavorite.isEnabled = false
                // 내 보관함에 있을 시 검색버튼 비활성화
                btnSearch.isEnabled = false
            }

            btnSearch.setOnClickListener {
                search = binding.etSearchBar.text.toString()
                getData(search, 80)
                saveHistory(search)
            }
        }
        loadHistory()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setFragment(f: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, f)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
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