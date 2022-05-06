package com.example.project

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.project.databinding.Board3MainBinding
import com.example.project.databinding.ItemDetailBinding
import com.example.project.model.CommentData
import com.example.project.model.ItemData
import com.example.project.recycler.CommentAdapter
import com.example.project.recycler.MyAdapter
import com.example.project.util.dateToString
import java.util.*

//수정했음, 데이터 전달 잘됨
class DetailActivity :  ToolbarBase() {
    private lateinit var binding: ItemDetailBinding
    private lateinit var context: Context

    var docId = ""

    override fun onCreate (savedInstancsState: Bundle?) {
        binding = ItemDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstancsState)
        //setContentView(R.layout.item_detail)
        getWindow().setFlags( // 화면 full로 채우기
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val email = intent.getStringExtra("email")
        val date = intent.getStringExtra("date")
        val data = intent.getStringExtra("docID")
        val imageYN = intent.getStringExtra("imageYN")

        docId = data.toString()
        //Toast.makeText(this, imageYN, Toast.LENGTH_SHORT).show()

        if (imageYN.contentEquals("1")) {
            val imgRef = MyApplication.storage
                .reference
                .child("images/${data}.jpg")

            Glide.with(this)
                .load(imgRef)
                .into(binding.detailImageView)
            //Toast.makeText(this, "image yes", Toast.LENGTH_SHORT).show()
        } else {
            //Toast.makeText(this, "image no", Toast.LENGTH_SHORT).show()
            binding.detailImageView.visibility = View.GONE
        }


        // datail 게시글 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_key)

        // 데이터 연결
        binding.detailTitleView.text = title
        binding.detailContentView.text = content
        binding.detailEmailView.text = email
        binding.detailDateView.text = date

        makeCommentRecycler()

        /*
        //댓글 EditText에 글 있을 시에만 버튼 활성화
        binding.detailCommentView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length > 0) { // 버튼 활성화
                    binding.detailCommentButton.setClickable(true)
                    binding.detailCommentButton.visibility = View.VISIBLE
                } else {
                    binding.detailCommentButton.setClickable(false)
                    binding.detailCommentButton.visibility = View.GONE
                }
            }
        })

         */

        val send_btn = findViewById<Button>(R.id.detailCommentButton)

        send_btn.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
            if (binding.detailCommentView.text.isNotEmpty()) //내용확인
                saveStore()
            else {
                Toast.makeText(this, "데이터가 모두 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //댓글 불러오기
    private fun makeCommentRecycler() {
        MyApplication.db.collection("comment")
            .whereEqualTo("docId", docId)
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<CommentData>()
                for (document in result) {
                    val item = document.toObject(CommentData::class.java)
                    item.commentId=document.id
                    itemList.add(item)
                }
                binding.detailRecyclerView.layoutManager= LinearLayoutManager(this)
                binding.detailRecyclerView.adapter= CommentAdapter(this, itemList)
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveStore(){
        val comment = mapOf(
            "docId" to docId,
            "email" to MyApplication.email,
            "content" to binding.detailCommentView.text.toString(), //내용 저장
            "date" to dateToString(Date())
        )

        MyApplication.db.collection("comment")
            .add(comment)
            .addOnFailureListener {
                Log.w("kkang", "data save error", it)
            }.addOnSuccessListener {
                Toast.makeText(this, "댓글이 등록되었습니다.",Toast.LENGTH_SHORT).show()
                finish()
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

    //뒤로가기 막기
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}