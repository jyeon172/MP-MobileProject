package com.example.project

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.project.databinding.Board3MainBinding
import com.example.project.databinding.ItemDetailBinding
import com.example.project.recycler.MyAdapter

//수정했음, 데이터 전달 잘됨
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ItemDetailBinding
    private lateinit var context: Context


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
        val data = intent.getStringExtra("docID")
        val imageYN = intent.getStringExtra("imageYN")

        Toast.makeText(this, imageYN, Toast.LENGTH_SHORT).show()

        if(imageYN.contentEquals("1")) {
            val imgRef = MyApplication.storage
                .reference
                .child("images/${data}.jpg")

            Glide.with(this)
                .load(imgRef)
                .into(binding.detailImageView)
            Toast.makeText(this, "image yes", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(this, "image no", Toast.LENGTH_SHORT).show()



        binding.detailTitleView.text = title
        binding.detailContentView.text = content
    }

}