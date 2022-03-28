package com.example.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project.databinding.ItemMainBinding
import kotlinx.android.synthetic.main.item_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ItemMainBinding

    override fun onCreate (savedInstancsState: Bundle?){
        binding = ItemMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstancsState)
        setContentView(R.layout.item_detail)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        detailTitleView.text = title
        detailContentView.text = content
        //detailTitleView.setText("제목: " + title) 이것도 가능
    }

}