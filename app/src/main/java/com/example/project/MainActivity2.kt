package com.example.project

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.project.databinding.ActivityAddBinding
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.MainMainBinding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var binding2: MainMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        binding2 = MainMainBinding.inflate(layoutInflater)

        getWindow().setFlags( // 화면 full로 채우기
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)

        //Add 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    override fun onStart() {
        super.onStart()
        if(!MyApplication.checkAuth()){
            setContentView(binding.root)
            //startActivity(Intent(this, MainActivity2::class.java))
        }else {
            //setContentView(R.layout.main_main) //툴바 있음
            //setContentView(binding2.root) //툴바 없음
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_main_auth->{ // 인증버튼
                startActivity(Intent(this, AuthActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }
}