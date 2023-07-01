package com.example.myaicte

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class TestAdapter(private val userList: ArrayList<Test>
,private val listener: OnItemClickListener): RecyclerView.Adapter<TestAdapter.userviewholder>() {


    inner class userviewholder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val instituteName: TextView = itemView.findViewById(R.id.institutename)
        val instituteState: TextView = itemView.findViewById(R.id.institutestate)
        val instituteType: TextView = itemView.findViewById(R.id.institutetype)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
        }



    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userviewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return  userviewholder(itemView)

    }

    override fun onBindViewHolder(holder: userviewholder, position: Int) {
        val course = userList[position]
        holder.instituteName.text = course.instituteName
        holder.instituteType.text = course.instituteType
        holder.instituteState.text = course.instituteState

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}



