package com.example.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project.databinding.ChatMainBinding


class ChatMain : AppCompatActivity() {
    private lateinit var binding : ChatMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChatMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 앱 구동시 LoginFragment 표시
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_frame, LoginFragment())
            .commit()
    }

    // ChatFragment로 프래그먼트 교체 (LoginFragment에서 호출할 예정)
    fun replaceFragment(bundle: Bundle) {
        val destination = ChatFragment()
        destination.arguments = bundle      // 닉네임을 받아옴
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_frame, destination)
            .commit()
    }
}