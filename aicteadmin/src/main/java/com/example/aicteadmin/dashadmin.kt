package com.example.aicteadmin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_dashadmin.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [dashadmin.newInstance] factory method to
 * create an instance of this fragment.
 */
class dashadmin : Fragment() {
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
        return inflater.inflate(R.layout.fragment_dashadmin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myview = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottomview)
        myview.visibility = View.GONE
        val myview1 = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottomview1)
        myview1.visibility = View.GONE

        //val dashtotalInstitute = editti?.text.toString.trim()


        val etTotalI = editti
        val etNewI = editnewi
        val etClosedI = editci
        val etTotalIn = edittin
        val etGirl = editge
        val etBoy = editbe
        val etFaculty = editf
        val etStudent = edits
        val etPlacement = editp

        setEditFirestore()

        sbmitdash?.setOnClickListener{
            val dashtotalInstitute = etTotalI.text.toString().trim()
            val dashnewInstitute = etNewI.text.toString().trim()
            val dashclosedInstitute = etClosedI.text.toString().trim()
            val dashtotalIntake = etTotalIn.text.toString().trim()
            val dashboy = etBoy.text.toString().trim()
            val dashgirl = etGirl.text.toString().trim()
            val dashfaculty = etFaculty.text.toString().trim()
            val dashstudent = etStudent.text.toString().trim()
            val dashplacement= etPlacement.text.toString().trim()

            val mapUpdate = mapOf(
                "totalInstitute" to dashtotalInstitute,
                "closedInstitute" to dashclosedInstitute,
                "newInstitute" to dashnewInstitute,
                "totalIntake" to dashtotalIntake,
                "girlE" to dashgirl,
                "boyE" to dashboy,
                "faculty" to dashfaculty,
                "studentP" to dashstudent,
                "placement" to dashplacement
            )

            db.collection("dashDetail").document("XsxUtn1fI1BOCxCoEyv2").update(mapUpdate)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Success update", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),it.toString(), Toast.LENGTH_SHORT).show()
                }


        }
    }

    private fun setEditFirestore() {
        val ref =  db.collection("dashDetail").document("XsxUtn1fI1BOCxCoEyv2")
            .get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("existadmindb", "DocumentSnapshot data: ${document.data}")
                    val res1 = document.getString("totalInstitute")
                    val res2 = document.getString("newInstitute")
                    val res3 = document.getString("closedInstitute")
                    val res4 = document.getString("totalIntake")
                    val res5 = document.getString("girlE")
                    val res6 = document.getString("boyE")
                    val res7 = document.getString("faculty")
                    val res8 = document.getString("studentP")
                    val res9 = document.getString("placement")

                    editti?.setText(res1)
                    editnewi?.setText(res2)
                    editci?.setText(res3)
                    edittin?.setText(res4)
                    editge?.setText(res5)
                    editbe?.setText(res6)
                    editf?.setText(res7)
                    edits?.setText(res8)
                    editp?.setText(res9)

                } else {
                    Log.d("notexistdb", "No such document")
                }

            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment dashadmin.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            dashadmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}