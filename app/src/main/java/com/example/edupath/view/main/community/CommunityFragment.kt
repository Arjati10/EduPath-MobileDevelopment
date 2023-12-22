package com.example.edupath.view.main.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edupath.R
import com.example.edupath.data.pref.Community
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.ref.WeakReference

class CommunityFragment : Fragment() {

    private lateinit var btnAddCommunity : CardView
    private lateinit var btnJoin : Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var communityRef: DatabaseReference
    private lateinit var rvCommunity: RecyclerView
    private val list = ArrayList<CommunityFragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        communityRef = firebaseDatabase.getReference("communities")

        rvCommunity = view.findViewById(R.id.rv_community)
        loadInfoCommunity()

        btnAddCommunity = view.findViewById(R.id.card_plusCommunity)

        btnAddCommunity.setOnClickListener{
            val intent = Intent(requireContext(), AddCommunityActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loadInfoCommunity() {
        val contextRef = WeakReference<Context>(requireContext())

        communityRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val context = contextRef.get() ?: return
                val listCommunity = ArrayList<Community>()
                for (dataSnapshot in snapshot.children) {
                    val community = dataSnapshot.getValue(Community::class.java)
                    community?.let { listCommunity.add(it) }
                }
                rvCommunity.adapter = ListCommunityAdapter(listCommunity)
                rvCommunity.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(context, "Failed to load communities: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}