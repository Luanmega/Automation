package com.example.luanmega.smartautomation


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_add_object.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast


/**
 * A simple [Fragment] subclass.
 */
class AddObjectFragment : Fragment(), View.OnClickListener {


    var mAuth : FirebaseAuth ?= null
    var user : FirebaseUser ?= null
    var data: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var mDatabase: DatabaseReference = data.getReference("User")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_add_object, container, false)
        val btn: Button = view.find(R.id.btnAddObj)
        btn.setOnClickListener(this)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        user = mAuth!!.currentUser


    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btnAddObj -> {
                //toast("alpjasjdkasndknaskdnkasndkasndkjnaskjdnaskjdnkasjndkas")
                addObject()
            }

            else -> {
            }
        }
    }

    fun addObject(){
        try {
            var firebaseData : DatabaseReference ?= mDatabase
            var area : String = txtObjArea.text.toString()
            var obj : String = txtObjName.text.toString()
            if(area != "" && obj != ""){
                var dev = Device(area, obj)

                val key = firebaseData!!.child("User").child(user!!.uid).child("Area").push().key
                //it.uuid = key
                firebaseData!!.child("User").child(user!!.uid).child("${dev.Area}").child(dev.NombreObjeto).setValue("0")
                toast("Evento añadido con exito")
            }
            else{
                toast("No se permiten campos vacios")
            }


        }catch (e: Exception){
            toast("Error: ${e.message}")
        }

    }



}// Required empty public constructor

data class Device(
    var Area : String = "",
    var NombreObjeto : String = ""
)