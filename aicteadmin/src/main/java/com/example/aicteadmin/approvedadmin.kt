package com.example.aicteadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_approvedadmin.*
import kotlinx.android.synthetic.main.fragment_closedadmin.*
import kotlinx.android.synthetic.main.fragment_dashadmin.*
import kotlinx.android.synthetic.main.fragment_editapproved.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [approvedadmin.newInstance] factory method to
 * create an instance of this fragment.
 */
class approvedadmin : Fragment() {
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
        return inflater.inflate(R.layout.fragment_approvedadmin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myview = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottomview)
        myview.visibility = View.VISIBLE
        val myview1 = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottomview1)
        myview1.visibility = View.GONE

        sbmitapproved?.setOnClickListener{
            val approvedID = editapprovedID?.text.toString().trim()
            val approvedName = editapprovedname?.text.toString().trim()
            val approvedCity = editapprovedcity?.text.toString().trim()
            val approvedState = editapprovedstate?.text.toString().trim()
            val approvedCourse = editapprovedcourse?.text.toString().trim()
            val approvedTime = editapprovedtime?.text.toString().trim()
            val approvedType = editapprovedtype?.text.toString().trim()
            val approvedShift = editapprovedShift?.text.toString().trim()
            val approvedFaculty = editapprovedfaculty?.text.toString().trim()
            val approvedAddress = editapprovedaddress?.text.toString().trim()


            val approvedAdd = mapOf(
                "instituteAddress" to approvedAddress,
                "instituteID" to approvedID,
                "instituteName" to approvedName,
                "instituteCity" to approvedCity,
                "instituteState" to approvedState,
                "instituteCourse" to approvedCourse,
                "instituteTime" to approvedTime,
                "instituteType" to approvedType,
                "instituteShift" to approvedShift,
                "instituteFaculty" to approvedFaculty

            )

            db.collection("approvedInstitute").add(approvedAdd)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Success Add Approved Institute", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment approvedadmin.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            approvedadmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}