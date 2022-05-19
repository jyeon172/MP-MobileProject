package com.example.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.Board3MainBinding
import com.example.project.databinding.ItemMainBinding
import com.example.project.model.ItemData
import com.example.project.recycler.MyAdapter
import com.example.project.util.myCheckPermission
import com.google.firebase.firestore.Query

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

        //spinner
        val b3List = listOf("전체", "토익", "자격증")
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, b3List)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 -> {
                        setContentView(binding.root)
                        val menuOption = "전체"
                        makeRecyclerView()
                    }
                    1 -> {
                        setContentView(binding.root)
                        val menuOption = "토익"
                        makeRecyclerMenu(menuOption)
                    }
                    2 -> {
                        setContentView(binding.root)
                        val menuOption = "자격증"
                        makeRecyclerMenu(menuOption)
                    }
                    else -> {}
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    // 툴바 뒤로가기 버튼
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
            .orderBy("date", Query.Direction.DESCENDING)
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
            //.orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId=document.id
                    itemList.add(item)
                }
                var itemSort = itemList.sortedByDescending { it.date }
                binding.mainRecyclerView.layoutManager= LinearLayoutManager(this)
                binding.mainRecyclerView.adapter= MyAdapter(this, itemSort)
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }

    }

    //뒤로가기 막기
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}