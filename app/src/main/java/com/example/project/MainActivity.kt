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

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    lateinit var bind: ItemMainBinding
    lateinit var binding: ActivityMainBinding
    lateinit var binding2: MainMainBinding

    lateinit var navigationView1: NavigationView
    lateinit var drawerLayout1: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ItemMainBinding.inflate(layoutInflater)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding2 = MainMainBinding.inflate(layoutInflater)

        setContentView(binding2.root)

        getWindow().setFlags( // 화면 full로 채우기
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getFCMToken()

        val toolbar: Toolbar = findViewById(R.id.toolbar) //툴바 사용여부 결정(기본적으로 사용)
        if (useToolbar()) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false) //표시되는 제목의 표시유무, false면 설정한 툴바의 이름이 화면에 보임
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
            supportActionBar?.setHomeAsUpIndicator(R.drawable.navi_menu) // 홈버튼 이미지 변경
            drawerLayout1 = findViewById(R.id.drawer_layout) //드로어 레이아웃 생성

            // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
            navigationView1 = findViewById(R.id.nav_view)
            navigationView1.setNavigationItemSelectedListener(this)

        } else {
            toolbar.visibility = View.GONE
        }

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
            startActivity(Intent(this, MainActivity2::class.java))
        }else {
            setContentView(binding2.root) // 툴바O, 최신글O
//            makeRecycler1View()
//            makeRecycler2View() //세부2 최신글
//            makeRecycler3View() //세부3 최신글
        }
    }
    private fun makeRecycler1View() {
        MyApplication.db.collection("board1")
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
                binding2.mainBoard1RecyclerView.layoutManager= LinearLayoutManager(this)
                    .also { it.orientation = LinearLayoutManager.HORIZONTAL }
                binding2.mainBoard1RecyclerView.adapter= Main2Adapter(this, itemSort)
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
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

    //툴바 관련 함수


    //툴바 사용할지 말지, 기본값은 true. 사용 안 할려면 다른 액티비티에서 false로 바꾸기
    /*
    override fun useToolbar(): Boolean {
        return false
    }
     */
    fun useToolbar(): Boolean {
        return true
    }

    // 로그인 메뉴 띄우기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                drawerLayout1.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }

            R.id.menu_main_auth->{ // 인증버튼
                startActivity(Intent(this, AuthActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() { // 드로어 레이아웃 뒤로가기 처리, 안 할 시 앱종료됨
        if(drawerLayout1.isDrawerOpen(GravityCompat.START)){
            drawerLayout1.closeDrawers()
            // 테스트를 위해 뒤로가기 버튼시 Toast 메시지
            Toast.makeText(this,"뒤로가기", Toast.LENGTH_SHORT).show()
        } else{
            super.onBackPressed()
        }
    }

    // 드로어 내 아이템 클릭 이벤트 처리하는 함수
    override fun onNavigationItemSelected(item: MenuItem): Boolean { //네비게이션뷰에서 각 메뉴 클릭시
        when(item.itemId){
            R.id.menu_item1-> {
                Toast.makeText(this,"세부 1 clicked",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Board1Activity::class.java))
            }
            R.id.menu_item2-> {
                Toast.makeText(this,"세부 2 clicked",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Board2Activity::class.java))
            }
            R.id.menu_item3-> {
                Toast.makeText(this,"세부 3 clicked",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Board3Activity::class.java))
                //카테고리 정보 받아오기
            }
        }
        return false
    }

}