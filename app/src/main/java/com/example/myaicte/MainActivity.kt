package com.example.myaicte

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.google.firebase.ktx.Firebase
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private var backPressedTime = 0L
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.framelayout, HomeFragment()).commit()


        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val db = FirebaseFirestore.getInstance()
        //val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            val ref1 = db.collection("userDetail").document(uid)
            ref1.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    //Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    //Log.d(TAG, "Current data: ${snapshot.data}")
                    val res1 = snapshot.getString("userFullName")
                    val mynavView = findViewById(R.id.nav_view) as NavigationView
                    var menuItem = mynavView.menu.findItem(R.id.nav_profile)
                    menuItem.title = res1
                } else {
                    //Log.d(TAG, "Current data: null")
                }
            }

        }
        /*
        val uid = user?.uid
        val db = FirebaseFirestore.getInstance()
        //val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            val ref1 = db.collection("userDetail").document(uid)
            ref1.get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("existdb", "DocumentSnapshot data: ${document.data}")
                    val res1 = document.getString("userFullName")
                    val mynavView = findViewById(R.id.nav_view) as NavigationView
                    var menuItem = mynavView.menu.findItem(R.id.nav_profile)
                    menuItem.title = res1

                } else {
                    Log.d("notexistdb", "No such document")
                }
            }

        }*/





        drawerLayout = findViewById<DrawerLayout>(R.id.mydraw)
        val navView  = findViewById<NavigationView>(R.id.nav_view)
        navView.itemIconTintList=null
        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close)
        //toggle.setDrawerIndicatorEnabled(false);
        //toggle.setHomeAsUpIndicator(R.mipmap.ham)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()




        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){
                //R.id.nav_profile -> replaceFragment(Profile(),it.title.toString())
                R.id.nav_profile -> replaceFragment(Profile(), "Profile")
                R.id.nav_home -> replaceFragment(HomeFragment(),"Home")
                    //sendMessage()
                R.id.nav_dashboard -> replaceFragment(DashboardFragment(),"DashBoard")
                R.id.nav_approved -> replaceFragment(testFragment(),"Approved Institute")
                //R.id.nav_approved -> replaceFragment(ApprovedInstitute(),it.title.toString())
                R.id.nav_closedinstitute -> replaceFragment(closed(),"Closed Institute")
                R.id.nav_contactUs -> replaceFragment(Contact_US(),"Contact Us")


                R.id.nav_exit -> {
                    //Toast.makeText(this,"Clicked Exit ", Toast.LENGTH_SHORT).show()
                    myQuitApp()
                    true
                }
                R.id.nav_logout -> {
                    //Toast.makeText(this,"Clicked Exit ", Toast.LENGTH_SHORT).show()
                    mylogoutApp()
                    true
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment:Fragment, title:String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout,fragment)
        //fragmentTransaction.hide(Fragment())
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)

    }
    private fun openact(){
        val intent = Intent(this, contact::class.java)
        setTitle("Contact Us")
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

         when(item.itemId){
             R.id.exitoption -> {

                 myQuitApp()
                 true
             }

             R.id.developedbyoption-> {
                 Toast.makeText(this,"Developed By Akhilesh Rawat", Toast.LENGTH_SHORT).show()
                 true
             }

             R.id.contactoption-> {
                 //Toast.makeText(this,"Contact Us", Toast.LENGTH_SHORT).show()
                 replaceFragment(Contact_US(),this.title.toString())
                 true
             }

             R.id.shareoption-> {
                 myShareApp()
                 true
             }
             R.id.editoption-> {
                 replaceFragment(EditProfile(),this.title.toString())
             }
             /*R.id.filteroption-> {
                 val fm = supportFragmentManager
                 val myf = filterDialog()
                 //filterDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                 myf.dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                 myf.show(fm, " Dialog ttt")
                 true
             }*/

        }

        if(toggle.onOptionsItemSelected(item)){
            return true
        }else {
            return super.onOptionsItemSelected(item)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return true
    }

    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
        }else{
            Toast.makeText(applicationContext,"Press Back Again To Exit ", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()

    }

    private fun myShareApp(){
        val intent= Intent()
        intent.action=Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent,"Share To:"))
    }

    private fun myQuitApp() {
        val alertDialog : AlertDialog = AlertDialog.Builder(this, R.style.AlertDialogStyleExit).create()
        //alertDialog.setTitle("Exit Dialog")
        alertDialog.setMessage("Do you want to exit?")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"YES"){
            dialog, which -> finish()
            dialog.dismiss()
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"NO"){
            dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()

    }



    private  fun mylogoutApp(){
        val alertDialog : AlertDialog = AlertDialog.Builder(this, R.style.AlertDialogStyleLogout).create()
        //alertDialog.setTitle("LogOut Dialog")
        alertDialog.setMessage("Do you want to Logout?")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"YES"){
                dialog, which -> Firebase.auth.signOut()
            val intent = Intent(this, signIn::class.java)
            startActivity(intent)
            finish()
            dialog.dismiss()
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"NO"){
                dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()

    }





}