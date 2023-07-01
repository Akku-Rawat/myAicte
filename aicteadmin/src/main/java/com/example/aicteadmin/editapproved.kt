package com.example.aicteadmin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_approvedadmin.*
import kotlinx.android.synthetic.main.fragment_dashadmin.*
import kotlinx.android.synthetic.main.fragment_deleteapproved.*
import kotlinx.android.synthetic.main.fragment_editapproved.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [editapproved.newInstance] factory method to
 * create an instance of this fragment.
 */
class editapproved : Fragment() {
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
        return inflater.inflate(R.layout.fragment_editapproved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findapprovedbtn?.setOnClickListener{
            val approvededitname = editapprovedfindname?.text.toString().trim()
            val ref1 =  db.collection("approvedInstitute").whereEqualTo("instituteName", approvededitname)
                .get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        //Log.d(TAG, "${document.id} => ${document.data}")
                        val res2 = document.id
                        val ref3 = db.collection("approvedInstitute").document(res2).get()
                            .addOnSuccessListener { document ->
                                if (document != null) {
                                    //Log.d("existadmindb", "DocumentSnapshot data: ${document.data}")
                                    val res1 = document.getString("instituteAddress")
                                    val res11 = document.getString("instituteID")
                                    val res3 = document.getString("instituteName")
                                    val res4 = document.getString("instituteCity")
                                    val res5 = document.getString("instituteState")
                                    val res6 = document.getString("instituteCourse")
                                    val res7 = document.getString("instituteTime")
                                    val res8 = document.getString("instituteType")
                                    val res9 = document.getString("instituteShift")
                                    val res10 = document.getString("instituteFaculty")

                                    changeapprovedID?.setText(res11)
                                    changeapprovedname?.setText(res3)
                                    changeapprovedcity?.setText(res4)
                                    changeapprovedstate?.setText(res5)
                                    changeapprovedcourse?.setText(res6)
                                    changeapprovedtime?.setText(res7)
                                    changeapprovedtype?.setText(res8)
                                    changeapprovedShift?.setText(res9)
                                    changeapprovedfaculty?.setText(res10)
                                    changeapprovedaddress?.setText(res1)

                                } else {
                                    Log.d("notexistdb", "No such document")
                                }

                            }

                        //Toast.makeText(requireContext(),"Success Delete ${approvededitname}", Toast.LENGTH_SHORT).show()

                        //editcloseddeletename?.setText(res1)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"No such Institute", Toast.LENGTH_SHORT).show()
                }

            //editapproveddeletename?.setText("")
        }

        sbmitchangeapproved?.setOnClickListener {
            val approvedID = changeapprovedID?.text.toString().trim()
            val approvedName = changeapprovedname?.text.toString().trim()
            val approvedCity = changeapprovedcity?.text.toString().trim()
            val approvedState = changeapprovedstate?.text.toString().trim()
            val approvedCourse = changeapprovedcourse?.text.toString().trim()
            val approvedTime = changeapprovedtime?.text.toString().trim()
            val approvedType = changeapprovedtype?.text.toString().trim()
            val approvedShift = changeapprovedShift?.text.toString().trim()
            val approvedFaculty = changeapprovedfaculty?.text.toString().trim()
            val approvedAddress = changeapprovedaddress?.text.toString().trim()


            val changeapprovedAdd = mapOf(
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

            val approvededitname1 = editapprovedfindname?.text.toString().trim()
            val ref1 =  db.collection("approvedInstitute").whereEqualTo("instituteName", approvededitname1)
                .get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        //Log.d(TAG, "${document.id} => ${document.data}")
                        val res2 = document.id
                        val ref3 = db.collection("approvedInstitute").document(res2).update(changeapprovedAdd)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(),"Success update", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(),it.toString(), Toast.LENGTH_SHORT).show()
                            }

                    }
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
         * @return A new instance of fragment editapproved.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            editapproved().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}