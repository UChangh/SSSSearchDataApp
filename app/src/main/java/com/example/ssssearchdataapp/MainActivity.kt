package com.example.ssssearchdataapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssssearchdataapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val main = binding.root
        setContentView(main)
    }
}