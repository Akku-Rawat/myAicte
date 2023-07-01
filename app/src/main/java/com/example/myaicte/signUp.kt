package com.example.myaicte

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myaicte.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_in.view.*

class signUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        emailfocuslistner()
        passwordfocuslistner()
        confirmpasswordfocuslistner()

        binding.textView.setOnClickListener {
            val intent = Intent(this, signIn::class.java)
            startActivity(intent)
        }



        binding.button.setOnClickListener {

            binding.emailLayout.helperText = validateEmail()
            binding.passwordLayout.helperText = validatePass()

            val validemail = binding.emailLayout.helperText == null
            val validpass = binding.passwordLayout.helperText == null
            val validcpass = binding.confirmPasswordLayout.helperText == null

            if(validemail && validpass && validcpass){
                val radiogrp1 = findViewById<RadioGroup>(R.id.radiogender)
                val radiogrp2 = findViewById<RadioGroup>(R.id.radioposition)

                val radioid1 : Int = radiogrp1.checkedRadioButtonId
                val radioid2 : Int = radiogrp2.checkedRadioButtonId

                val radio1 = findViewById<RadioButton>(radioid1) // gender
                val radio2 = findViewById<RadioButton>(radioid2) // position


                val username = binding.usernameEt.text.toString()
                val userfullname = binding.userfullnameEt.text.toString()
                val gender = radio1?.text
                val position = radio2?.text
                val email = binding.emailEt.text.toString()
                val pass = binding.passET.text.toString()
                val confirmPass = binding.confirmPassEt.text.toString()

                val userAdd = mapOf(
                    "userName" to username,
                    "userFullName" to userfullname,
                    "userEmail" to email,
                    "userPosition" to position,
                    "userGender" to gender,
                )


                if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                    if (pass == confirmPass) {
                        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                val uid = firebaseAuth.currentUser?.uid
                                if (uid != null) {
                                    val ref1 = db.collection("userDetail").document(uid)
                                    ref1.set(userAdd).addOnSuccessListener {
                                        Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
                                    }

                                }
                                val intent = Intent(this, signIn::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                            }
                        }
                    } else {
                        Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                        //binding.confirmPasswordLayout.helperText = "Password Not Matching"
                    }
                } else {
                    Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

                }
            }else{
                invalidForm()
            }


        }
    }

    private fun confirmpasswordfocuslistner() {
        binding.confirmPassEt.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.confirmPasswordLayout.helperText = validatePass1()
            }
        }
    }

    private fun validatePass1(): String? {

        val passtext1 = binding.confirmPassEt.text.toString()
        val passtext2 = binding.passET.text.toString()
        if(passtext1.length<6){
            return "Minimum 6 character required"
        }
        if(passtext1.length>16){
            return "Max 17 character allowed"
        }
        if(!passtext1.matches(".*[A-Z].*".toRegex())){
            return "Must Contain atleast 1 Uppercase"
        }
        if(!passtext1.matches(".*[a-z].*".toRegex())){
            return "Must Contain atleast 1 lowercase"
        }
        if(!passtext1.matches(".*[0-9].*".toRegex())){
            return "Must Contain atleast 1 number"
        }
        if(passtext1 != passtext2){
            return "Password not Matching"
        }
        return null
    }

    private fun invalidForm() {
        var message = ""
        if(binding.emailLayout.helperText != null){
            message += "\n\n Email : " + binding.emailLayout.helperText
        }
        if(binding.passwordLayout.helperText != null){
            message += "\n\n Pass : " + binding.passwordLayout.helperText
        }
        if(binding.confirmPasswordLayout.helperText != null){
            message += "\n\n Pass : " + binding.confirmPasswordLayout.helperText
        }


        AlertDialog.Builder(this)
            .setTitle("Invalid")
            .setMessage(message)
            .setPositiveButton("okay"){_,_ ->
                //
            }
            .show()
    }

    private fun passwordfocuslistner() {
        binding.passET.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.passwordLayout.helperText = validatePass()
            }
        }
    }

    private fun validatePass(): String? {
        val passtext = binding.passET.text.toString()
        if(passtext.length<6){
            return "Minimum 6 character required"
        }
        if(passtext.length>16){
            return "Max 17 character allowed"
        }
        if(!passtext.matches(".*[A-Z].*".toRegex())){
            return "Must Contain atleast 1 Uppercase"
        }
        if(!passtext.matches(".*[a-z].*".toRegex())){
            return "Must Contain atleast 1 lowercase"
        }
        if(!passtext.matches(".*[0-9].*".toRegex())){
            return "Must Contain atleast 1 number"
        }
        return null
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

}