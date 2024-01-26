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
import com.example.ssssearchdataapp.externaldatas.Document
import com.example.ssssearchdataapp.fragments.ImageSearch
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    companion object {
        var items = mutableListOf<Document>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val main = binding.root
        setContentView(main)

        val fragment = ImageSearch.newInstance("","")
        setFragment(fragment)

        binding.btnSearch.setOnClickListener {
            getData(binding.etSearchBar.text.toString(), 10)
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setFragment(f: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, f)
            setReorderingAllowed(true)
            addToBackStack("")
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

    fun getData(query: String, size: Int) = lifecycleScope.launch {
        val response = DataRequestURLs.kakaoNetwork.getItem(KakaoAPIKey.REST_API_KEY, query, size)
        Log.d("Parsing Sex ::", response.toString())
        items = response.documents
    }
}