package com.example.myaicte

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_dashboard.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }



   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)
    }*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("dashDetail").document("XsxUtn1fI1BOCxCoEyv2")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("existdb", "DocumentSnapshot data: ${document.data}")
                    val res1 = document.getString("totalInstitute")
                    val res2 = document.getString("newInstitute")
                    val res3 = document.getString("closedInstitute")
                    val res4 = document.getString("totalIntake")
                    val res5 = document.getString("girlE")
                    val res6 = document.getString("boyE")
                    val res7 = document.getString("faculty")
                    val res8 = document.getString("studentP")
                    val res9 = document.getString("placement")


                    textti?.setText(res1)
                    textni?.setText(res2)
                    textci?.setText(res3)
                    texttin?.setText(res4)
                    textge?.setText(res5)
                    textbe?.setText(res6)
                    textf?.setText(res7)
                    textsp?.setText(res8)
                    textp?.setText(res9)

                } else {
                    Log.d("notexistdb", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("errordb", "get failed with ", exception)


            }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}