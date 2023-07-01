package com.example.aicteadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_deleteapproved.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [deleteapprovedbtn.newInstance] factory method to
 * create an instance of this fragment.
 */
class deleteapproved : Fragment() {
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
        return inflater.inflate(R.layout.fragment_deleteapproved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myview1 = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottomview1)
        myview1.visibility = View.GONE

        deleteapprovedbtn?.setOnClickListener{
            val approveddeleteName = editapproveddeletename?.text.toString().trim()
            val ref1 =  db.collection("approvedInstitute").whereEqualTo("instituteName", approveddeleteName)
                .get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        //Log.d(TAG, "${document.id} => ${document.data}")
                        val res2 = document.id
                        val ref3 = db.collection("approvedInstitute").document(res2).delete()
                        Toast.makeText(requireContext(),"Success Delete ${approveddeleteName}", Toast.LENGTH_SHORT).show()

                        //editcloseddeletename?.setText(res1)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"No such Institute", Toast.LENGTH_SHORT).show()
                }

            editapproveddeletename?.setText("")
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment deleteapproved.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            deleteapproved().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}