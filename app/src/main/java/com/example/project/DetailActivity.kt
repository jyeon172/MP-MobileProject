package com.example.project

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.project.databinding.ChatMainBinding
import com.example.project.databinding.CommentMainBinding
import com.example.project.databinding.ItemDetailBinding
import com.example.project.model.CommentData
import com.example.project.recycler.CommentAdapter
import com.example.project.util.dateToString
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

//수정했음, 데이터 전달 잘됨
class DetailActivity :  ToolbarBase() {
    private lateinit var binding: ItemDetailBinding
    private lateinit var context: Context

    var docId = ""
    val TAG = "DetailActivity"
    private var myToken : String = ""
    var emailId =""

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
        val itemtoken = intent.getStringExtra("token")

        val user1 = email?.split('@')?.get(0).toString()
//        Log.d("kkang", user1)

        val user2 = MyApplication.email?.split('@')?.get(0).toString()
//        Log.d("kkang", user2)

        var chatId = ""
        if (user1 < user2) {
            chatId = user1 + user2
        } else {
            chatId = user2 + user1
        }
//        Log.d("kkang", chatId)


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
        showCount()

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

        val chat_btn = findViewById<Button>(R.id.chattingButton)

        chat_btn.setOnClickListener {
            val intent = Intent(this, ChatMain::class.java)
            intent.putExtra("docId", docId)
            startActivity(intent)
        }

        val user_btn = findViewById<ImageButton>(R.id.userBtn)

        user_btn.setOnClickListener {
            val intent = Intent(this, ChatMain::class.java)
            intent.putExtra("docId", chatId)
            startActivity(intent)
        }

        // 정연: 댓글 통해 개인채팅 구현 못 함
//        val user_cmt_btn = findViewById<ImageButton>(R.id.userCmtBtn)
//
//        user_cmt_btn.setOnClickListener {
//            val intent = Intent(this, ChatMain::class.java)
//            intent.putExtra("docId", docId)
//            startActivity(intent)
//        }

        val fav_btn = findViewById<ImageButton>(R.id.favoriteButton)
        val fav_btn2 = findViewById<ImageButton>(R.id.favoriteButton2)
        val fav_cnt = findViewById<TextView>(R.id.favoriteTextview)
        fav_btn2.visibility = View.INVISIBLE

        fav_btn.setOnClickListener {
            Toast.makeText(this, "[favorite] clicked", Toast.LENGTH_SHORT).show()
            fav_btn2.visibility = View.VISIBLE
            fav_btn.visibility = View.INVISIBLE
            saveCount()
            showCount()
        }

        fav_btn2.setOnClickListener {
            Toast.makeText(this, "[favorite] unclicked", Toast.LENGTH_SHORT).show()
            fav_btn.visibility = View.VISIBLE
            fav_btn2.visibility = View.INVISIBLE
            showCancelCount()
        }

        val send_btn = findViewById<Button>(R.id.detailCommentButton)

        //토큰 가져오기
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            myToken = task.result
            Log.d("kkang", "FCM Token is ${myToken}")
        })

        send_btn.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
            // itemToken은 게시글 쓴 사람의 토큰
            if (itemtoken != null) {
                Log.d("itemtoken", itemtoken)
            }
            else{
                Log.e("itemtoken", "게시글 토큰 비워져있음")
            }

            if (binding.detailCommentView.text.isNotEmpty()&& itemtoken != null) { //내용확인
                saveStore()

                // myToken은 내 토큰
                Log.d("mytoken", myToken)

                // 게시글 토큰에게 알림 보내기
                PushNotification(
                    NotificationData("Study With", "내가 쓴 게시글에 댓글이 달렸어요!"),
                    itemtoken
                ).also{
                    sendNotification(it)
                }

            }

            else {
                Toast.makeText(this, "댓글이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveCount(){
        val countTest = mapOf(
            "docId" to docId,
            "email" to MyApplication.email,
        )

        MyApplication.db.collection("like")
            .add(countTest)
            .addOnFailureListener {
                Log.w("kkang", "data save error", it)
            }.addOnSuccessListener {
                Toast.makeText(this, "하트 저장",Toast.LENGTH_SHORT).show()

            }
    }

    private fun showCount() {
        MyApplication.db.collection("like")
            .whereEqualTo("docId", docId)
            //.orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                var cnt = 0
                for(document in result) {
                    cnt += 1
                }
                Log.d("kkang", "msg"+cnt)
                val countTest = findViewById<TextView>(R.id.favoriteTextview)
                countTest.text = "Like $cnt"
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "안됨", Toast.LENGTH_SHORT).show()
            }

    }

    private fun showCancelCount() {
        MyApplication.db.collection("like")
            .whereEqualTo("docId", docId)
            //.orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                var cnt = 0
                for(document in result) {
                    cnt += 1
                }
                cnt -= 1
                Log.d("kkang", "msg"+cnt)
                val countTest = findViewById<TextView>(R.id.favoriteTextview)
                countTest.text = "Like $cnt"
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "안됨", Toast.LENGTH_SHORT).show()
            }

    }

    //알람 띄우기
    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody()?.string()!!)
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }


    //댓글 불러오기
    @SuppressLint("SetTextI18n")
    private fun makeCommentRecycler() {
        MyApplication.db.collection("comment")
            .whereEqualTo("docId", docId)
            //.orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<CommentData>()
                for (document in result) {
                    val item = document.toObject(CommentData::class.java)
                    item.commentId=document.id
                    itemList.add(item)
                }
                var itemSort = itemList.sortedBy { it.date }
                val cnt = itemSort.size.toString()

                val com_btn = findViewById<TextView>(R.id.commentCnt)
                com_btn.text = "Comment $cnt"
                binding.detailRecyclerView.layoutManager= LinearLayoutManager(this)
                binding.detailRecyclerView.adapter= CommentAdapter(this, itemSort)
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
