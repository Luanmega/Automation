package com.example.luanmega.smartautomation


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luanmega.smartautomation.Recycler.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_objects_view.*
import android.content.Context
import android.app.Application
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.jetbrains.anko.support.v4.toast


/**
 * A simple [Fragment] subclass.
 */
class ObjectsViewFragment : Fragment() {

    private val animals: ArrayList<String> = ArrayList()
    var mAuth : FirebaseAuth?= null
    var user : FirebaseUser?= null
    var data: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mDatabase: DatabaseReference = data.getReference("User")
    var firebaseData : DatabaseReference ?= mDatabase

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater!!.inflate(R.layout.fragment_objects_view, container, false)

        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        user = mAuth!!.currentUser

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Loads animals into the ArrayList
        getItems()

        // Creates a vertical Layout Manager
        //rv_animal_list.layoutManager = LinearLayoutManager(context)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        rv_animal_list.layoutManager = GridLayoutManager(context, 2)

        // Access the RecyclerView Adapter and load the data into it
        rv_animal_list.adapter = RecyclerAdapter(animals, context)
    }

    fun addAnimals(list: ArrayList<objStatus>) {
        for(i in list){
            animals.add(i.Obj)
        }


    }

    fun getItems(){
        var objListener = object : ValueEventListener {
            val menu: MutableList<objStatus> = mutableListOf()
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var children = dataSnapshot!!.children
                dataSnapshot?.children.mapNotNullTo(menu) { it.getValue<objStatus>(objStatus::class.java) }
                var lista: ArrayList<objStatus> = ArrayList<objStatus>()
                for(i in menu.indices){
                    lista?.add(i, menu[i])
                    toast("${menu[i]}")
                }
                addAnimals(lista)
            }
            override fun onCancelled(databaseError: DatabaseError?) {
                toast("Se ha cancelado la obtencion de informacion")
            }
        }
        mDatabase.child("User").child("${user!!.uid}")!!.addValueEventListener(objListener)
    }
}// Required empty public constructor


data class objStatus(
    var Area: String = "",
    var Obj: String = "",
    var Status: String = ""
)
