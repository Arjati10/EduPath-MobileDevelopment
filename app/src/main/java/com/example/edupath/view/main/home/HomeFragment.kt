package com.example.edupath.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.edupath.R
import com.example.edupath.data.pref.Banner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class HomeFragment : Fragment() {
    private lateinit var rvBanner: RecyclerView
    private val list = ArrayList<Banner>()
    private lateinit var tvNames: TextView
    private lateinit var ivProfil: ImageView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        tvNames = view.findViewById(R.id.tv_name)
        ivProfil = view.findViewById(R.id.iv_profile)

        rvBanner = view.findViewById(R.id.rv_banner)
        rvBanner.setHasFixedSize(true)

        list.addAll(getListBanner())
        showRecyclerList()

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        return view
    }

    private fun getListBanner(): ArrayList<Banner>{
        val dataTitle = resources.getStringArray(R.array.banner_title)
        val dataDetail = resources.getStringArray(R.array.banner_detail)
        val dataPhoto = resources.getStringArray(R.array.banner_photo)

        val listBanner = ArrayList<Banner>()
        for (i in dataTitle.indices){
            val banner = Banner(dataTitle[i], dataDetail[i], dataPhoto[i])
            listBanner.add(banner)
        }
        return listBanner
    }

    private fun showRecyclerList(){
        rvBanner.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val listBannerAdapter = ListBannerAdapter(list)
        rvBanner.adapter = listBannerAdapter
       }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("name").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"

                    tvNames.text = name

                    try {
                        Glide
                            .with(this@HomeFragment)
                            .load(profileImage)
                            .placeholder(R.drawable.profil_user)
                            .into(ivProfil)
                    } catch (e: Exception){

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}