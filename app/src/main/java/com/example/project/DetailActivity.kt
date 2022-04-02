package com.example.project

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.project.databinding.ItemDetailBinding
//수정했음, 데이터 전달 잘됨
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ItemDetailBinding

    override fun onCreate (savedInstancsState: Bundle?){
        binding = ItemDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstancsState)
        //setContentView(R.layout.item_detail)
        getWindow().setFlags( // 화면 full로 채우기
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        binding.detailTitleView.text = title
        binding.detailContentView.text = content
        //detailTitleView.setText("제목: " + title) 이것도 가능
    }

}