package com.example.myaicte

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"




/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var imageurl : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        //profiletext?.setText("Akku")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val email = user?.email
        val db = FirebaseFirestore.getInstance()
        //val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            val ref1 = db.collection("userDetail").document(uid)
            ref1.get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("existdb", "DocumentSnapshot data: ${document.data}")
                    val res1 = document.getString("userName")
                    val res2 = document.getString("userEmail")
                    val res3 = document.getString("userGender")
                    val res4 = document.getString("userPosition")
                    val res5 = document.getString("userFullName")
                    val res6 = document.getString("userAbout")

                    user_profile_name?.setText(res1)
                    userEmail?.setText(res2)
                    userFullName?.setText(res5)
                    userPosition?.setText(res4)
                    userGender?.setText(res3)
                    userAbout?.setText(res6)

                } else {
                    Log.d("notexistdb", "No such document")
                }
            }

            val uid = Firebase.auth.currentUser?.uid
            val store = FirebaseStorage.getInstance()
            val gsReference = store.getReferenceFromUrl("gs://myaicteakku.appspot.com/images/$uid")


            val localfile = File.createTempFile("MyPic", "jpg")
            gsReference.getFile(localfile).addOnSuccessListener {
                Log.d("mypic", "working")
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                user_profile_photo.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.d("mypicnot", "notworking")
            }

        }

        editbtn?.setOnClickListener {
            replaceFragment(EditProfile(), "Hello")
        }

    }

    private fun replaceFragment(fragment:Fragment, title:String){
        //val fragmentManager = supportFragmentManager
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame1,fragment)
        //fragmentTransaction.hide(Fragment())
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit()

        //drawerLayout.closeDrawers()
        //setTitle(title)

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

