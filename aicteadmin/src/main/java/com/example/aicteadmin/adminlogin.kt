package com.example.aicteadmin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.aicteadmin.databinding.ActivityAdminloginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_adminlogin.*

class adminlogin : AppCompatActivity() {
    private lateinit var binding: ActivityAdminloginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    //var flag : Boolean = false
    //var flag1 : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogin)

        binding = ActivityAdminloginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        emailfocuslistner()
        //passwordfocuslistner()
        binding.textView1.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialogforget1, null)
            builder.setView(view)
            //val myforget = forgetdialog?.text.toString()
            val myforget = view.findViewById<EditText>(R.id.forgetdialog1)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, i ->
                myforgetFun(myforget)
            })

            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, i -> })
            builder.show()


        }
        //val email1 =  emailEt.text.toString()
        //val email1 = binding.emailEt.text.toString()

        //Log.d("first" ," ${flag} for ${email1}")

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                checkAdminExist(email, pass)
                //Toast.makeText(this, "You are Not An Admin", Toast.LENGTH_SHORT).show()
                //Log.d("bool", "DocumentSnapshot data: ${flag1}")
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun emailfocuslistner() {
        binding.emailEt.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.emailLayout.helperText = validateEmail()
            }
        }
    }

    private fun validateEmail(): String? {
        val emailtext = binding.emailEt.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailtext).matches()){
            return "Invalid Email Address"
        }
        return null
    }

    private fun checkAdminExist(email: String, pass: String) {
        val tempemail = email.toString()
        val mypass = pass.toString()
        val db = FirebaseFirestore.getInstance()
        val refq = db.collection("adminDetail").whereEqualTo("adminEmail", tempemail)
        refq.get().addOnSuccessListener {
            if (it.isEmpty) {
                Toast.makeText(this, "You are Not An Admin", Toast.LENGTH_SHORT).show()
            } else {
                for (document in it.documents) {
                    val res2 = document.id
                    val ref3 = db.collection("adminDetail").document(res2).get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                Log.d("docexist", "DocumentSnapshot data: ${document.data}")
                                firebaseAuth.signInWithEmailAndPassword(email, mypass)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            //Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                                            Toast.makeText(
                                                this,
                                                "Wrong Password",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            }
                        }
                }
            }
            //Toast.makeText(this, "You are Not An Admin", Toast.LENGTH_SHORT).show()

        }
    }


    private fun myforgetFun(forgett: EditText) {
        //val emailAddress = "7akcena@gmail.com"
        //val emailAddress = forgetdialog?.text.toString()
        val emailAddress = forgett.text.toString()

        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show()
                } else {
                    //Toast.makeText(this,"Email Not Sent", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, this.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        var flag: Boolean = false
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
