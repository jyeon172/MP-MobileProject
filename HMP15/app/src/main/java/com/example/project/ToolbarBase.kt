package com.example.project

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.project.databinding.BaseToolbarBinding
import com.example.project.databinding.Board3MainBinding
import com.google.android.material.navigation.NavigationView

open class ToolbarBase: AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: BaseToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?){
        binding = BaseToolbarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.base_toolbar, null) as DrawerLayout
        val activityContainer = fullView.findViewById<View>(R.id.activity_content) as FrameLayout
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        val toolbar: Toolbar = findViewById(R.id.toolbar) //툴바 사용여부 결정(기본적으로 사용)
        if (useToolbar()) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false) //표시되는 제목의 표시유무, false면 설정한 툴바의 이름이 화면에 보임
            toolbar.title = "Study With (스윗)"
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
            supportActionBar?.setHomeAsUpIndicator(R.drawable.navi_menu) // 홈버튼 이미지 변경
            drawerLayout = findViewById(R.id.drawer_layout)

            // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
            navigationView = findViewById(R.id.nav_view)
            navigationView.setNavigationItemSelectedListener(this)

        } else {
            toolbar.visibility = View.GONE
        }
    }

    //툴바 사용할지 말지, 기본값은 true. 사용 안 할려면 다른 액티비티에서 false로 바꾸기
    /*
    override fun useToolbar(): Boolean {
        return false
    }
     */
    protected open fun useToolbar(): Boolean {
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home->{ // 메뉴 버튼
                drawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
            R.id.menu_main_auth->{ // 인증버튼
                startActivity(Intent(this, AuthActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() { // 드로어 레이아웃 뒤로가기 처리, 안 할 시 앱종료됨
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers()
            // 테스트를 위해 뒤로가기 버튼시 Toast 메시지
            Toast.makeText(this,"뒤로가기", Toast.LENGTH_SHORT).show()
        } else{
            super.onBackPressed()
        }
    }

    // 드로어 내 아이템 클릭 이벤트 처리하는 함수
    override fun onNavigationItemSelected(item: MenuItem): Boolean { //네비게이션뷰에서 각 메뉴 클릭시
        when(item.itemId){
            R.id.menu_item1-> Toast.makeText(this,"세부 1 clicked",Toast.LENGTH_SHORT).show()
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