package com.example.project.recycler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.DetailActivity
import com.example.project.MyApplication

import com.example.project.databinding.ItemMainBinding
import com.example.project.model.ItemData
import com.google.firebase.storage.StorageReference


class MyAdapter(val context: Context, val itemList: MutableList<ItemData>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data:ItemData){
            binding.itemEmailView.text=data.email
            binding.itemDateView.text=data.date
            binding.itemContentView.text=data.content
            binding.itemTitleView.text=data.title

            //확인용, 나중에 지울 것
            binding.categoryView.text=data.category

            //binding.itemImageView.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemMainBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.bind(itemList[position])

        holder.itemView.setOnClickListener {
            //Toast.makeText(context, "Click: ${itemList[position].email}", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("title", itemList[position].title)
            intent.putExtra("content", itemList[position].content)
            ContextCompat.startActivity(holder.itemView.context,intent, null)
        }

        //스토리지 이미지 다운로드........................
        val imgRef= MyApplication.storage
            .reference
            .child("images/${data.docId}.jpg")

        Glide.with(context )
            .load(imgRef)
            .into(holder.binding.itemImageView)

        /*
        //제목이 없는 경우 제목 레이아웃 제거
        if (data.title.isNullOrBlank()){
            holder.binding.itemTitleView.visibility = View.GONE
        }
        */

        /*
        //사진이 없는 경우 사진 레이아웃 제거
        if (data.docId.isNullOrBlank()){
            holder.binding.itemImageView.visibility = View.GONE
        }
        */

    }

}

