package com.example.project

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.project.databinding.ActivityAddBinding
import com.example.project.databinding.Board1MainBinding
import com.example.project.databinding.Board3MainBinding
import com.example.project.util.dateToString
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    lateinit var bind: Board1MainBinding
    lateinit var filePath: String
    //추가
    var radioChecked:String = "전체"

    val TAG = "AddActivity"
    private var myToken : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        getWindow().setFlags( // 화면 full로 채우기
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)

        //라디오 버튼 누르면
        binding.radioGroup.setOnCheckedChangeListener{group, checkedId ->
            when(checkedId){
                R.id.radioBtnAll -> radioChecked = "전체"
                R.id.radioBtn1 -> radioChecked = "앱"
                R.id.radioBtn2 -> radioChecked = "웹"
                R.id.radioBtn3 -> radioChecked = "알고리즘"
                R.id.radioBtn4 -> radioChecked = "보안"
                R.id.radioBtn5 -> radioChecked = "게임"
            }
        }

        //Add 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.add_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_key)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode===10 && resultCode=== Activity.RESULT_OK){
            Glide
                .with(getApplicationContext())
                .load(data?.data)
                .apply(RequestOptions().override(250, 200))
                .centerCrop()
                .into(binding.addImageView)


            val cursor = contentResolver.query(data?.data as Uri,
                arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null);
            cursor?.moveToFirst().let {
                filePath=cursor?.getString(0) as String
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.menu_add_gallery){
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            startActivityForResult(intent, 10)
            binding.imageText.visibility = View.GONE
        }else if(item.itemId === R.id.menu_add_save){
            if(binding.addEditView.text.isNotEmpty() //내용확인
                and binding.addEditViewTitle.text.isNotEmpty()) { //제목확인
                saveStore()
            }
            /*if(binding.addImageView.drawable !== null && binding.addEditView.text.isNotEmpty()){
                //store 에 먼저 데이터를 저장후 document id 값으로 업로드 파일 이름 지정
                saveStore()
            }*/else {
                Toast.makeText(this, "데이터가 모두 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }

        }
        else if(item.itemId === android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    //....................
    private fun saveStore(){

        //토큰 가져오기
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            myToken = task.result.toString()
            Log.d("kkang", "FCM Token is ${myToken}")

            // 테스트
            Log.d("addActiviy_mytoken", myToken)

            // 게시글 토큰 저장하기
            MyApplication.prefs.setString("addToken",myToken)


        })

        // 게시글 토큰 받아오기
        val addtoken2 = MyApplication.prefs.getString("addToken",myToken)
        Log.d("addActivity22", addtoken2)

        //이미지유무
        var imageYN = "0"
        if (binding.addImageView.drawable != null)
            imageYN = "1"
        else
            imageYN = "0"
        //추가
        val category = radioChecked
        //add............................
        val data = mapOf(
            "email" to MyApplication.email,
            "title" to binding.addEditViewTitle.text.toString(), //제목 저장
            "content" to binding.addEditView.text.toString(), //내용 저장
            "date" to dateToString(Date()),
            "category" to category,
            "imageYN" to imageYN,
            "token" to addtoken2
        )
        //데이터 저장하기. 사진이 있을 때 없을 떄 따로 하는 건?
        if (binding.addImageView.drawable != null) {
            MyApplication.db.collection("board1")
                .add(data)
                .addOnSuccessListener {
                    //store 에 데이터 저장후 document id 값으로 storage 에 이미지 업로드
                    uploadImage(it.id)
                }
                .addOnFailureListener {
                    Log.w("kkang", "data save error", it)
                }
        }
        else {
            MyApplication.db.collection("board1")
                .add(data)
                .addOnFailureListener {
                    Log.w("kkang", "data save error", it)
                }.addOnSuccessListener {
                    Toast.makeText(this, "데이터가 저장되었습니다.",Toast.LENGTH_SHORT).show()
                    finish()
                }
            //다시 글을 쓴 카테고리 보드 보여주기, 카테고리 전달해주기
        }
    }
    private fun uploadImage(docId: String){
        //add............................
        val storage = MyApplication.storage
        //storage 를 참조하는 StorageReference 를 만든다.
        val  storageRef: StorageReference = storage.reference
        //실제 업로드하는 파일을 참조하는 StorageReference 를 만든다.
        val imgRef: StorageReference = storageRef.child("images/${docId}.jpg")
        //파일 업로드
        var file = Uri.fromFile(File(filePath))
        imgRef.putFile(file)
            .addOnFailureListener {
                Log.d("kkang"," failure............."+it)
            }.addOnSuccessListener {
                Toast.makeText(this, "데이터가 저장되었습니다.",Toast.LENGTH_SHORT).show()
                finish()
            }

    }
}