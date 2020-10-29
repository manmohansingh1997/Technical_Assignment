package com.sonuproject.pago_assignment.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.sonuproject.pago_assignment.Fragment.DashboardFragment
import com.sonuproject.pago_assignment.R

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem: MenuItem? = null
    lateinit var context: Context
    lateinit var pref: SharedPreferences

    // Editor for Shared preferences
    lateinit var editor: SharedPreferences.Editor
    private val PREF_NAME = "Test"
    val ISLOGIN = "isLogin"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById(R.id.drawerlayout)
        coordinatorLayout = findViewById(R.id.coordinatorlayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.framelayout)
        navigationView = findViewById(R.id.navigationview)

        context = this
        pref = context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();

        setUpToolbar()
        openDashboard()


        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
            )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()




        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it





            when (it.itemId) {
                R.id.dashboard -> {
                    openDashboard()
                    drawerLayout.closeDrawers()
                }
                R.id.logout -> {

                    val dialog = android.app.AlertDialog.Builder(this)
                    dialog.setTitle("Confirmation")
                    dialog.setMessage("Sure want to Logout")
                    dialog.setPositiveButton("Yes") { text, listener ->
                        val intent = Intent(this, login::class.java)
                        setPrefValue(ISLOGIN, false)
                        //sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
                        startActivity(intent)
                        finish()
                    }
                    dialog.setNegativeButton("Cancel") { text, listener ->
                        openDashboard()
                        drawerLayout.closeDrawers()
                    }
                    dialog.create()
                    dialog.show()


                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    fun getPrefValue(key: String?): String? {
        return pref.getString(key, "")
    }

    fun setPrefValue(key: String?, `val`: String?) {
        editor.putString(key, `val`).commit()
    }

    fun setPrefValue(key: String?, `val`: Boolean?) {
        editor.putBoolean(key, `val`!!).commit()
    }

    fun getPrefBooleanValue(key: String?): Boolean {
        return pref.getBoolean(key, false)
    }

    fun isLogin(): Boolean {
        return getPrefBooleanValue(ISLOGIN)
    }

    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Order List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard() {
        val fragment =
            DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.framelayout)

        when (frag) {


            !is DashboardFragment -> {
                navigationView.menu.getItem(0).setChecked(true)
                openDashboard()
            }
            else -> super.onBackPressed()

        }

    }
}