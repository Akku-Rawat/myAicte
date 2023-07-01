package com.example.myaicte

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myaicte.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_forget.*
import kotlinx.android.synthetic.main.dialog_forget.view.*

class signIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        emailfocuslistner()
        binding.textView.setOnClickListener {
            val intent = Intent(this, signUp::class.java)
            startActivity(intent)
        }

        binding.textView1.setOnClickListener{

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forget,null)
            builder.setView(view)
            //val myforget = forgetdialog?.text.toString()
            val myforget = view.findViewById<EditText>(R.id.forgetdialog)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, i ->
                myforgetFun(myforget)
            })

            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, i ->  })
            builder.show()


        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
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

    private fun myforgetFun(forgett : EditText) {
        //val emailAddress = "7akcena@gmail.com"
        //val emailAddress = forgetdialog?.text.toString()
        val emailAddress = forgett.text.toString()


        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Email Sent", Toast.LENGTH_SHORT).show()
                }else{
                    //Toast.makeText(this,"Email Not Sent", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this,this.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}