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

// test

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

        /*myCheckPermission(this)
        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()) {
                startActivity(Intent(this, AddActivity::class.java))
            } else {
                Toast.makeText(this, "인증을 먼저 진행해 주세요", Toast.LENGTH_SHORT).show()
            }
        }*/

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






    /*
    private fun makeRecyclerView(){
        // 컬랙션을 모두 가져오기
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId=document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager=LinearLayoutManager(this)
                binding.mainRecyclerView.adapter= MyAdapter(this, itemList)
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
    }*/
}