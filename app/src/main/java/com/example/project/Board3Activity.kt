package com.example.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.Board3MainBinding
import com.example.project.databinding.ItemMainBinding
import com.example.project.model.ItemData
import com.example.project.recycler.MyAdapter
import com.example.project.util.myCheckPermission

class Board3Activity: ToolbarBase() {

    private lateinit var binding: Board3MainBinding
    lateinit var bind: ItemMainBinding
    lateinit var big: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = Board3MainBinding.inflate(layoutInflater)
        bind = ItemMainBinding.inflate(layoutInflater)
        big = ActivityMainBinding.inflate(layoutInflater)
        getWindow().setFlags( // 화면 full로 채우기
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        myCheckPermission(this)
        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()) {
                startActivity(Intent(this, AddActivity::class.java))
            } else { //아마 필요없을듯
                Toast.makeText(this, "인증을 먼저 진행해 주세요", Toast.LENGTH_SHORT).show()
            }
        }

        //Board3 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.board3_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_key)

    }

    override fun onStart() {
        super.onStart()
        setContentView(binding.root)

        //카테고리 정보 받아와서 출력하는 걸로 바꾸기
        //makeRecyclerMenu()
        makeRecyclerView()

        var option = findViewById<Button>(R.id.popupMenu)
        option.setOnClickListener {
            var menu = PopupMenu(applicationContext, it)

            menuInflater?.inflate(R.menu.popup_board3, menu.menu)
            menu.show()

            //클릭하면 액티비티 바뀌고 레이아웃에 버튼, 밑에 리사이클러뷰
            //카테고리 바꾸면 텍스트 바뀌고, 리사이클러뷰 조건에 맞게 재생성
            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.popupMenu1 -> {
                        //setContentView(R.layout.menu1_main)
                        setContentView(binding.root)
                        val menuOption = "전체"
                        //startActivity(Intent(this, MenuActivity::class.java).putExtra("menuOption", menuOption))
                        binding.menuName.text = menuOption
                        //지금은 전체 카테고리에 안 들어있는 것도 불러옴, 나중에 밑에 있는 걸로 바꾸기
                        makeRecyclerView()
                        //makeRecyclerMenu(menuOption)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.popupMenu2 -> {
                        //setContentView(R.layout.menu2_main)
                        setContentView(binding.root)
                        val menuOption = "토익"
                        //startActivity(Intent(this, MenuActivity::class.java).putExtra("menuOption", menuOption))
                        binding.menuName.text = menuOption
                        //카테고리해당아이템불러오기
                        makeRecyclerMenu(menuOption)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.popupMenu3 -> {
                        //setContentView(R.layout.menu3_main)
                        setContentView(binding.root)
                        val menuOption = "자격증"
                        //startActivity(Intent(this, MenuActivity::class.java).putExtra("menuOption", menuOption))
                        binding.menuName.text = menuOption
                        makeRecyclerMenu(menuOption)
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

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
                binding.mainRecyclerView.layoutManager= LinearLayoutManager(this)
                binding.mainRecyclerView.adapter= MyAdapter(this, itemList)
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun makeRecyclerMenu(option:String){
        MyApplication.db.collection("news")
            .whereEqualTo("category", option)
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId=document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager= LinearLayoutManager(this)
                binding.mainRecyclerView.adapter= MyAdapter(this, itemList)
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }

    }

}