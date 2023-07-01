package com.example.myaicte

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// on below line we are creating a course rv adapter class.
class CourseRVAdapter(private var courseList: ArrayList<CourseRVModal>
) : RecyclerView.Adapter<CourseRVAdapter.CourseViewHolder>() {

    var onItemClick : ((CourseRVModal) -> Unit)? = null


    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our course name text view and our image view.
        val institutename: TextView = itemView.findViewById(R.id.institutename)
        val institutestate: TextView = itemView.findViewById(R.id.institutestate)
        val institutetype: TextView = itemView.findViewById(R.id.institutetype)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false
        )
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseRVAdapter.CourseViewHolder, position: Int) {
        // on below line we are setting data to our text view and our image view.
        val course = courseList[position]
        holder.institutename.text = course.institutename
        holder.institutetype.text = course.institutetype
        holder.institutestate.text = course.institutestate

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(course)
        }

    }

    override fun getItemCount(): Int {
        // on below line we are returning
        // our size of our list
        return courseList.size
    }


}


