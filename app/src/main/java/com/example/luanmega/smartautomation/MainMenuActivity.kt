package com.example.luanmega.smartautomation

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.app_bar_main_menu.*
import kotlinx.android.synthetic.main.fragment_add_object.*
import kotlinx.android.synthetic.main.nav_header_main_menu.*

class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private var mAuth : FirebaseAuth? = null
    private var user : FirebaseUser? = null
    private var fManager : FragmentManager = getSupportFragmentManager()
    private var mainFragment : ObjectsViewFragment = ObjectsViewFragment()
    private val fragmentAddObject : AddObjectFragment = AddObjectFragment()
    var supp = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, activity_main_menu, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        activity_main_menu.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        mAuth = FirebaseAuth.getInstance()
        user = mAuth!!.currentUser



    }

    override fun onBackPressed() {
        if (activity_main_menu.isDrawerOpen(GravityCompat.START)) {
            activity_main_menu.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        cambiarDatos()
        //supportFragmentManager.beginTransaction().add(R.id.FrameContainer, mainFragment)
        fManager.beginTransaction()!!.add(R.id.FrameContainer, mainFragment).commit()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_agregarObjeto -> {
                // Ir a agregarObjeto fragment

                var fragTrans : FragmentTransaction = getSupportFragmentManager().beginTransaction()
                fragTrans.replace(R.id.FrameContainer, fragmentAddObject).commit()
            }
            R.id.nav_modificarObjeto -> {
                //Ir a configurarObjeto fragment
            }
            R.id.nav_borrarObjeto -> {
                //Ir a borrarObjeto fragment
            }
            R.id.nav_configurarUsuario -> {
                //Ir a configuracionUsuario fragment
            }
            R.id.nav_cerrarSesion -> {
                //Cerrar sesion e ir a pantalla de inicio de sesion
            }
        }

        activity_main_menu.closeDrawer(GravityCompat.START)
        return true
    }

    private fun cambiarDatos(){
        nombreUsuario.text = user!!.displayName.toString()
        emailUsuario.text = user!!.email.toString()
        Picasso.get().load(user!!.photoUrl).into(imagenUsuario)

    }

    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {

        val transaction = manager.beginTransaction()
        transaction.add(frameId, fragment)

        transaction.commit()

    }

}
