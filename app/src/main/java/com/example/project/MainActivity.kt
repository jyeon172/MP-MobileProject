package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.project.databinding.ActivityMainBinding
import com.example.project.MyApplication
import com.example.project.databinding.ItemMainBinding
import com.example.project.databinding.MainMainBinding
import com.example.project.model.ItemData
import com.example.project.recycler.MyAdapter
import com.example.project.util.myCheckPermission
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ToolbarBase(){

    lateinit var bind: ItemMainBinding
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ItemMainBinding.inflate(layoutInflater)
        binding = ActivityMainBinding.inflate(layoutInflater)

        getWindow().setFlags( // 화면 full로 채우기
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.root)

        getFCMToken()

    }

    private fun getFCMToken(): String? {
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {  task ->
            if(!task.isSuccessful) {
                Log.w("kkang", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            token = task.result
            Log.d("kkang", "FCM Token is ${token}")
        })
        return token
    }


    override fun onStart() {
        super.onStart()
        if(!MyApplication.checkAuth()){
            //binding.logoutTextView.visibility= View.VISIBLE
            //binding.mainRecyclerView.visibility=View.GONE
            setContentView(R.layout.activity_main)
        }else {
            //binding.logoutTextView.visibility= View.GONE
            //binding.mainRecyclerView.visibility=View.VISIBLE
            setContentView(R.layout.main_main)
            //makeRecyclerView()
        }
    }

}