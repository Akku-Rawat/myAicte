package com.example.myaicte

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClosedInstitute.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClosedInstitute : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var mylist : ArrayList<CourseRVModal>
    private lateinit var templist : ArrayList<CourseRVModal>
    private lateinit var cadapter: CourseRVAdapter
    private lateinit var myadapter: CourseRVAdapter
    private lateinit var mysearch : androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approved_institute, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ApprovedInstitute.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ApprovedInstitute().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        mysearch = itemView.findViewById(R.id.sv)
        mysearch.clearFocus()


        recyclerView = itemView.findViewById(R.id.rv)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        mylist = ArrayList()
        templist = ArrayList()
        mylist.add(CourseRVModal( "A M C . Engineering College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A P S College Of Engineering","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A S N Pharmacy College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A M C . Engineering College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A P S College Of Engineering","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A S N Pharmacy College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A M C . Engineering College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A P S College Of Engineering","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A S N Pharmacy College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A M C . Engineering College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A P S College Of Engineering","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A S N Pharmacy College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A M C . Engineering College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A P S College Of Engineering","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))
        mylist.add(CourseRVModal( "A S N Pharmacy College","1-3328468010", "26 Km Kanakapura Road Bangalore 82", "Bangalore Urban ","Karnataka", "Unaided - Private", "360", "Full Time", "MCA", "1st Shift"))


        cadapter = CourseRVAdapter(mylist)
        recyclerView.adapter = cadapter

        cadapter.onItemClick = {
            val intent = Intent(requireContext(), detailActivity::class.java )
            intent.putExtra("Name",it)
            startActivity(intent)
        }

        mysearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //recyclerView.adapter = CourseRVAdapter(templist)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                myfilter(newText)
                return false
            }

        })

    }

    private fun myfilter(newText : String?){
        templist.clear()
        val searchtext = newText!!.lowercase(Locale.getDefault())

        if(searchtext.isNotEmpty()){

            mylist.forEach{
                if(it.institutename.lowercase(Locale.getDefault()).contains(searchtext)){

                    templist.add(it)

                }
            }
            //cadapter!!.notifyDataSetChanged()
            //recyclerView.adapter = CourseRVAdapter(templist)
            //recyclerView.adapter!!.notifyDataSetChanged()
            myadapter = CourseRVAdapter(templist)
            recyclerView.adapter = myadapter
            recyclerView.adapter!!.notifyDataSetChanged()
            myadapter.onItemClick = {
                val intent = Intent(requireContext(), detailActivity::class.java)
                intent.putExtra("Name",it)
                startActivity(intent)
            }



        }else{

            templist.clear()
            templist.addAll(mylist)
            //cadapter!!.notifyDataSetChanged()
            //recyclerView.adapter = CourseRVAdapter(templist)
            //recyclerView.adapter!!.notifyDataSetChanged()
            myadapter = CourseRVAdapter(templist)
            recyclerView.adapter = myadapter
            recyclerView.adapter!!.notifyDataSetChanged()
            myadapter.onItemClick  = {
                val intent = Intent(requireContext(), detailActivity::class.java)
                intent.putExtra("Name",it)
                startActivity(intent)
            }
            myadapter = CourseRVAdapter(templist)
            recyclerView.adapter = myadapter

        }

    }
}