package com.example.project.recycler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project.DetailActivity
import com.example.project.databinding.ItemMain2Binding
import com.example.project.databinding.ItemMainBinding
import com.example.project.model.ItemData

//메인화면 세부3 연결 어댑터
class Main3Adapter (val context: Context, val itemList: List<ItemData>): RecyclerView.Adapter<Main3Adapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemMain2Binding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: ItemData){
            binding.item2EmailView.text=data.email
            //binding.itemDateView.text=data.date
            binding.item2ContentView.text=data.content
            binding.item2TitleView.text=data.title
            //if (data.imageYN == "1")
                //binding.imgIcon.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemMain2Binding.inflate(layoutInflater))
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
            intent.putExtra("token", data.token)
            ContextCompat.startActivity(holder.itemView.context,intent, null)
        }

    }

}