package com.example.myaicte

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myaicte.databinding.ActivityTestactivityBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_testactivity.*

class testactivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTestactivityBinding
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testactivity)

        val actionbar = supportActionBar
        actionbar!!.title = "Institute Details"
        actionbar.setDisplayHomeAsUpEnabled(true)


        val course = intent.getParcelableExtra<Test>("testt")
        if(course != null){

            val textviewname: TextView = findViewById(R.id.textViewname)
            val textviewid: TextView = findViewById(R.id.textViewnum)
            val textviewaddress: TextView = findViewById(R.id.textViewaddress)
            val textviewcity: TextView = findViewById(R.id.textViewcity)
            val textviewstate: TextView = findViewById(R.id.textViewstate)
            val textviewtype: TextView = findViewById(R.id.textViewtype)
            val textviewstaff: TextView = findViewById(R.id.textViewfaculty)
            val textviewtime: TextView = findViewById(R.id.textViewtime)
            val textviewcategory: TextView = findViewById(R.id.textViewcourse)
            val textviewshift: TextView = findViewById(R.id.textViewshift)

            textviewname.text = course.instituteName
            //textViewname?.setText(course.instituteName)
            textviewid.text = course.instituteID
            textviewaddress.text = course.instituteAddress
            textviewcity.text = course.instituteCity
            textviewstate.text = course.instituteState
            textviewtype.text = course.instituteType
            textviewstaff.text = course.instituteFaculty
            textviewtime.text = course.instituteTime
            textviewcategory.text = course.instituteCourse
            textviewshift.text = course.instituteShift

        }




    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}