package com.example.aicteadmin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_deleteclosed.*
import kotlinx.android.synthetic.main.fragment_editapproved.*
import kotlinx.android.synthetic.main.fragment_editclosed.*
import kotlinx.android.synthetic.main.fragment_editclosed.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [editclosed.newInstance] factory method to
 * create an instance of this fragment.
 */
class editclosed : Fragment() {
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
        return inflater.inflate(R.layout.fragment_editclosed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findclosedbtn?.setOnClickListener{
            val closededitname = editclosedfindname?.text.toString().trim()
            val ref1 =  db.collection("closedInstitute").whereEqualTo("instituteName", closededitname)
                .get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        //Log.d(TAG, "${document.id} => ${document.data}")
                        val res2 = document.id
                        val ref3 = db.collection("closedInstitute").document(res2).get()
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

                                    changeclosedID?.setText(res11)
                                    changeclosedname?.setText(res3)
                                    changeclosedcity?.setText(res4)
                                    changeclosedstate?.setText(res5)
                                    changeclosedcourse?.setText(res6)
                                    changeclosedtime?.setText(res7)
                                    changeclosedtype?.setText(res8)
                                    changeclosedShift?.setText(res9)
                                    changeclosedfaculty?.setText(res10)
                                    changeclosedaddress?.setText(res1)

                                } else {
                                    Log.d("notexistdb", "No such document")
                                }

                            }

                        //Toast.makeText(requireContext(),"Success Delete ${closededitname}", Toast.LENGTH_SHORT).show()

                        //editcloseddeletename?.setText(res1)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"No such Institute", Toast.LENGTH_SHORT).show()
                }

            //editcloseddeletename?.setText("")
        }

        sbmitchangeclosed?.setOnClickListener {
            val closedID = changeclosedID?.text.toString().trim()
            val closedName = changeclosedname?.text.toString().trim()
            val closedCity = changeclosedcity?.text.toString().trim()
            val closedState = changeclosedstate?.text.toString().trim()
            val closedCourse = changeclosedcourse?.text.toString().trim()
            val closedTime = changeclosedtime?.text.toString().trim()
            val closedType = changeclosedtype?.text.toString().trim()
            val closedShift = changeclosedShift?.text.toString().trim()
            val closedFaculty = changeclosedfaculty?.text.toString().trim()
            val closedAddress = changeclosedaddress?.text.toString().trim()


            val changeclosedAdd = mapOf(
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

            val closededitname1 = editclosedfindname?.text.toString().trim()
            val ref1 =  db.collection("closedInstitute").whereEqualTo("instituteName", closededitname1)
                .get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        //Log.d(TAG, "${document.id} => ${document.data}")
                        val res2 = document.id
                        val ref3 = db.collection("closedInstitute").document(res2).update(changeclosedAdd)
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
         * @return A new instance of fragment editclosed.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            editclosed().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}