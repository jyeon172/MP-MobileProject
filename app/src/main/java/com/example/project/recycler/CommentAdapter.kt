package com.example.project.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.CommentMainBinding
import com.example.project.model.CommentData
import com.example.project.model.ItemData


class CommentAdapter(val context: Context, val itemList: MutableList<CommentData>): RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: CommentMainBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: CommentData){
            binding.commentId.text=data.email
            binding.commentDate.text=data.date
            binding.commentText.text=data.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(CommentMainBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: CommentAdapter.MyViewHolder, position: Int) {
        val data = itemList.get(position)
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}