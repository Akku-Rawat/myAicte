package com.example.myaicte

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isEmpty
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_closed.*
import kotlinx.android.synthetic.main.fragment_closed.clik
import kotlinx.android.synthetic.main.fragment_test.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [testFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class testFragment : Fragment() , TestAdapter.OnItemClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var userlist : ArrayList<Test>
    private lateinit var templist : ArrayList<Test>
    private lateinit var myadapter: TestAdapter
    private lateinit var cadapter: TestAdapter
    private  var db1 = Firebase.firestore
    private lateinit var mysearch : androidx.appcompat.widget.SearchView
    private val RQ_SPEECH_REC = 100
    //lateinit var toggle : ActionBarDrawerToggle
    //lateinit var drawerLayout: DrawerLayout


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
        return inflater.inflate(R.layout.fragment_test, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db1 = FirebaseFirestore.getInstance()
        //mysearch = itemView.findViewById(R.id.sv)
        //mysearch.clearFocus()


        recyclerView = view.findViewById(R.id.rv)
        mysearch = view.findViewById(R.id.sv)
        mysearch.clearFocus()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userlist = arrayListOf()


        clik?.setOnClickListener {
            askSpeechInput()
        }


        db1.collection("approvedInstitute")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("db1test", "${document.id} => ${document.data}")
                    val user: Test? = document.toObject<Test>(Test::class.java)
                    userlist.add(user!!)

                }

                recyclerView.adapter = TestAdapter(userlist, this)
            }
            .addOnFailureListener { exception ->
                Log.d("db1error", "Error getting documents: ", exception)
            }

        templist = ArrayList()

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

        cadapter = TestAdapter(userlist,this)
        //cadapter = CourseRVAdapter(userArrayList)
        recyclerView.adapter = cadapter

        /*cadapter.onItemClick = {
            val intent = Intent(requireContext(), testactivity::class.java )
            intent.putExtra("testt",it)
            startActivity(intent)
        }*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val res = result?.get(0).toString()
            // textmy?.setText(res)

            myfilter(res)

        }
    }

    private fun askSpeechInput(){
        if(!SpeechRecognizer.isRecognitionAvailable(requireContext())){
            Toast.makeText(requireContext(), "Not Available", Toast.LENGTH_SHORT).show()
        }else{
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Institute Name")
            startActivityForResult(i, RQ_SPEECH_REC)
        }
    }


    private fun myfilter(newText : String?){
        templist.clear()
        val searchtext = newText!!.lowercase(Locale.getDefault())

        if(searchtext.isNotEmpty()){

            userlist.forEach{
                if(it.instituteName?.lowercase(Locale.getDefault())?.contains(searchtext) == true){

                    templist.add(it)

                }
            }

            if(templist.isEmpty()){
                emptyView?.visibility = View.VISIBLE
                //recyclerView.visibility = View.GONE
            }else{
                emptyView?.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }

            //emptyView?.visibility = View.GONE


            myadapter = TestAdapter(templist,this)
            recyclerView.adapter = myadapter
            recyclerView.adapter!!.notifyDataSetChanged()
            /*myadapter.onItemClick = {
                val intent = Intent(requireContext(), testactivity::class.java )
                intent.putExtra("testt",it)
                startActivity(intent)
            }*/

        }else{

            templist.clear()
            templist.addAll(userlist)
            //cadapter!!.notifyDataSetChanged()
            //recyclerView.adapter = CourseRVAdapter(templist)
            //recyclerView.adapter!!.notifyDataSetChanged()
            myadapter = TestAdapter(templist,this)
            emptyView?.visibility = View.GONE
            recyclerView.adapter = myadapter
            recyclerView.adapter!!.notifyDataSetChanged()
            myadapter = TestAdapter(templist,this)
            recyclerView.adapter = myadapter
            /*myadapter.onItemClick = {
                val intent = Intent(requireContext(), testactivity::class.java )
                intent.putExtra("testt",it)
                startActivity(intent)
            }*/

        }

    }

    /*private  fun echange(){
        val db1 = FirebaseFirestore.getInstance()
        db1.collection("instituteDetail")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("recycler firebase error", error.message.toString())
                        return
                    }
                    for(dc : DocumentChange in value?.documentChanges!!){

                        if(dc.type == DocumentChange.Type.ADDED)
                            userArrayList.add(dc.document.toObject(CourseRVModal::class.java))

                    }
                }
            })
    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment testFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            testFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        //Toast.makeText(requireContext(), "Item $position clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), testactivity::class.java )
        intent.putExtra("testt",userlist.get(position))
        //intent.putExtra("instituteName",userlist.get(position))
        //intent.putExtra("instituteState",userlist.get(position))
        startActivity(intent)

    }


}