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
import com.example.project.recycler.Main2Adapter
import com.example.project.recycler.Main3Adapter
import com.example.project.recycler.MyAdapter
import com.example.project.util.myCheckPermission
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ToolbarBase(){

    lateinit var bind: ItemMainBinding
    lateinit var binding: ActivityMainBinding
    lateinit var binding2: MainMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ItemMainBinding.inflate(layoutInflater)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding2 = MainMainBinding.inflate(layoutInflater)

        getWindow().setFlags( // 화면 full로 채우기
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
            setContentView(R.layout.activity_main)
        }else {
            //setContentView(R.layout.main_main) //툴바 있음
            setContentView(binding2.root) //툴바 없음
            makeRecycler2View() //세부2 최신글
            makeRecycler3View() //세부3 최신글
        }
    }

    private fun makeRecycler2View() {
        MyApplication.db.collection("board2")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId=document.id
                    itemList.add(item)
                }
                var itemSort = itemList.sortedByDescending { it.date }
                itemSort = itemSort.subList(0,4)
                binding2.mainBoard2RecyclerView.layoutManager= LinearLayoutManager(this)
                    .also { it.orientation = LinearLayoutManager.HORIZONTAL }
                binding2.mainBoard2RecyclerView.adapter= Main2Adapter(this, itemSort)
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
    }
    private fun makeRecycler3View() {
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId=document.id
                    itemList.add(item)
                }
                var itemSort = itemList.sortedByDescending { it.date }
                itemSort = itemSort.subList(0,4)
                binding2.mainBoard3RecyclerView.layoutManager= LinearLayoutManager(this)
                    .also { it.orientation = LinearLayoutManager.HORIZONTAL }
                binding2.mainBoard3RecyclerView.adapter= Main3Adapter(this, itemSort)
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
    }

}