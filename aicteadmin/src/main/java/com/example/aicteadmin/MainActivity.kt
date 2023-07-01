package com.example.aicteadmin

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.aicteadmin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private var backPressedTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.framelayout, dashadmin()).commit()

        /*val click1 = findViewById<Button>(R.id.adminbtnFrag1)
        click1.setOnClickListener() {
            replaceFragment(dashadmin(), "TEstFrag1")
        }*/
        val db = Firebase.firestore

        drawerLayout = findViewById<DrawerLayout>(R.id.mydraw)
        val navView  = findViewById<NavigationView>(R.id.nav_view)
        val navView1  = findViewById<BottomNavigationView>(R.id.nav_bottomview)
        val navView2  = findViewById<BottomNavigationView>(R.id.nav_bottomview1)
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
                //sendMessage()
                R.id.nav_dashboard -> replaceFragment(dashadmin(), "DashBoard")
                R.id.nav_approved -> replaceFragment(approvedadmin(), "Add Approved Institute")
                R.id.nav_closedinstitute -> replaceFragment(closedadmin(), "Add Closed Institute")
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


        /*val etTotalI = findViewById<EditText>(R.id.editti)
        val etNewI = findViewById<EditText>(R.id.editnewi)
        val etClosedI = findViewById<EditText>(R.id.editci)
        val etTotalIn = findViewById<EditText>(R.id.edittin)
        val etGirl = findViewById<EditText>(R.id.editge)
        val etBoy = findViewById<EditText>(R.id.editbe)
        val etFaculty = findViewById<EditText>(R.id.editf)
        val etStudent = findViewById<EditText>(R.id.edits)
        val etPlacement = findViewById<EditText>(R.id.editp)*/

        //val btn = findViewById<Button>(R.id.sbmitdash)


        navView1.setOnItemSelectedListener {
            when (it.itemId) {
                //R.id.nav_profile -> replaceFragment(Profile(),it.title.toString())
                //sendMessage()
                R.id.nav_addapproved -> replaceFragment(approvedadmin(), "Add Approved Institute")
                R.id.nav_editapproved -> replaceFragment(editapproved(), "Edit Approved Institute")
                R.id.nav_deleteapproved -> replaceFragment(deleteapproved(), "Delete Approved Institute")
            }
            true
        }

        navView2.setOnItemSelectedListener {
            when (it.itemId) {
                //R.id.nav_profile -> replaceFragment(Profile(),it.title.toString())
                //sendMessage()
                R.id.nav_addapproved -> replaceFragment(closedadmin(), "Add Closed Institute")
                R.id.nav_editapproved -> replaceFragment(editclosed(), "Edit Closed Institute")
                R.id.nav_deleteapproved -> replaceFragment(deleteclosed(), "Delete Closed Institute")
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment, title:String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout,fragment)
        //fragmentTransaction.hide(Fragment())
        fragmentTransaction.commit()
        fragmentTransaction.addToBackStack(null);
        drawerLayout.closeDrawers()
        setTitle(title)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.adminoption_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when(item.itemId){
            R.id.exitoption -> {

                myQuitApp()
                true
            }

            R.id.developedbyoption-> {
                Toast.makeText(this,"Developed By Akhilesh Rawat", Toast.LENGTH_SHORT).show()
                true
            }


            R.id.shareoption-> {
                myShareApp()
                true
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
            val intent = Intent(this, adminlogin::class.java)
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

    private fun myShareApp(){
        val intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent,"Share To:"))
    }



}