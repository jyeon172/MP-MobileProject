package com.example.project.recycler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project.DetailActivity
import com.example.project.databinding.ItemMainBinding
import com.example.project.model.ItemData

class My2Adapter (val context: Context, val itemList: List<ItemData>): RecyclerView.Adapter<My2Adapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: ItemData){
            binding.itemEmailView.text=data.email
            binding.itemDateView.text=data.date
            binding.itemContentView.text=data.content
            binding.itemTitleView.text=data.title
            if (data.imageYN == "1")
                binding.imgIcon.visibility = View.VISIBLE
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
            intent.putExtra("docID", data.docId)
            intent.putExtra("imageYN", data.imageYN)
            intent.putExtra("email", data.email)
            intent.putExtra("date", data.date)
            ContextCompat.startActivity(holder.itemView.context,intent, null)
        }

    }

}