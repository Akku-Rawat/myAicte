package com.example.aicteadmin

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_closedadmin.*
import kotlinx.android.synthetic.main.fragment_dashadmin.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [closedadmin.newInstance] factory method to
 * create an instance of this fragment.
 */
class closedadmin : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val db = FirebaseFirestore.getInstance()

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
        return inflater.inflate(R.layout.fragment_closedadmin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setEditDeleteFirestore()

        val myview = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottomview)
        myview.visibility = View.GONE
        val myview1 = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottomview1)
        myview1.visibility = View.VISIBLE


        sbmitclosed?.setOnClickListener{
            val closedID = editclosedID?.text.toString().trim()
            val closedName = editclosedname?.text.toString().trim()
            val closedCity = editclosedcity?.text.toString().trim()
            val closedState = editclosedstate?.text.toString().trim()
            val closedCourse = editclosedcourse?.text.toString().trim()
            val closedTime = editclosedtime?.text.toString().trim()
            val closedType = editclosedtype?.text.toString().trim()
            val closedShift = editclosedShift?.text.toString().trim()
            val closedFaculty = editclosedfaculty?.text.toString().trim()
            val closedAddress = editclosedaddress?.text.toString().trim()


            val closedAdd = mapOf(
                "instituteAddress" to closedAddress,
                "instituteID" to closedID,
                "instituteName" to closedName,
                "instituteCity" to closedCity,
                "instituteState" to closedState,
                "instituteCourse" to closedCourse,
                "instituteTime" to closedTime,
                "instituteType" to closedType,
                "instituteShift" to closedShift,
                "instituteFaculty" to closedFaculty

            )


            db.collection("closedInstitute").add(closedAdd)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Success Add Closed Institute", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),it.toString(), Toast.LENGTH_SHORT).show()
                }


        }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment closedadmin.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            closedadmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}