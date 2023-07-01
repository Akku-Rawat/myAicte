package com.example.myaicte

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val db = FirebaseFirestore.getInstance()
    lateinit var imageurl : Uri


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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEditProfileFirestore()
        sbmiteditprofile?.setOnClickListener {
            val radiogenderid : Int = radioeditgender.checkedRadioButtonId
            val radioposid : Int = radioeditposition.checkedRadioButtonId
            val radio1 = view.findViewById<RadioButton>(radiogenderid)
            val radio2 = view.findViewById<RadioButton>(radioposid)

            val userfullname = nameEt.text.toString()
            val gender = radio1?.text
            val position = radio2?.text
            val email = emailEt.text.toString()
            val about = aboutEt?.text.toString()

            val userUpdate = mapOf(
                "userFullName" to userfullname,
                "userEmail" to email,
                "userPosition" to position,
                "userGender" to gender,
                "userAbout" to about
            )

            val uid = Firebase.auth.currentUser?.uid
            if (uid != null) {
                val ref1 = db.collection("userDetail").document(uid).update(userUpdate)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(),"Success update", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(),it.toString(), Toast.LENGTH_SHORT).show()
                    }

            }
            uploadImage()
        }

        val user = Firebase.auth.currentUser
        //user?.photoUrl =
        val uid = user?.uid
        user_profile_photoedit?.setOnClickListener {
            selectImage()
        }

    }

    private fun uploadImage() {

        //val formatter = SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault())
        //val now = Date()
        val uid = Firebase.auth.currentUser?.uid
        val filename = uid
        val storeref = FirebaseStorage.getInstance().getReference("images/$filename")

        storeref.putFile(imageurl)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Image Uploaded", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Image Not Uploaded", Toast.LENGTH_SHORT).show()
            }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){

            imageurl = data?.data!!
            user_profile_photoedit.setImageURI(imageurl)

        }

    }

    private fun setEditProfileFirestore() {
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val db = FirebaseFirestore.getInstance()
        //val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            val ref1 = db.collection("userDetail").document(uid)
            ref1.get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("existdb", "DocumentSnapshot data: ${document.data}")
                    val res2 = document.getString("userEmail")
                    val res5 = document.getString("userFullName")
                    val res3 = document.getString("userGender")
                    val res4 = document.getString("userPosition")
                    val res6 = document.getString("userAbout")


                    if (res3 == "Male"){
                        radioeditmale.isChecked = true
                    }else{
                        radioeditfemale.isChecked = true
                    }

                    if (res4 == "Student"){
                        radioeditstudent.isChecked = true
                    }else{
                        radioeditfaculty.isChecked = true
                    }
                    val uid = Firebase.auth.currentUser?.uid
                    val store = FirebaseStorage.getInstance()
                    val gsReference = store.getReferenceFromUrl("gs://myaicteakku.appspot.com/images/$uid")


                    val localfile = File.createTempFile("MyPic", "jpg")
                    gsReference.getFile(localfile).addOnSuccessListener {
                        Log.d("mypic", "working")
                        val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                        user_profile_photoedit.setImageBitmap(bitmap)
                    }.addOnFailureListener {
                        Log.d("mypicnot", "notworking")
                    }

                    emailEt?.setText(res2)
                    nameEt?.setText(res5)
                    aboutEt?.setText(res6)

                } else {
                    Log.d("notexistdb", "No such document")
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
         * @return A new instance of fragment EditProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}