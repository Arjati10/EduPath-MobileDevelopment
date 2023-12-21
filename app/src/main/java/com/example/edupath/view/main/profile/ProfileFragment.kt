package com.example.edupath.view.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.edupath.R
import com.example.edupath.databinding.ActivityEditProfileBinding
import com.example.edupath.view.auth.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class ProfileFragment : Fragment() {

    private lateinit var btnLogout: Button
    private lateinit var btnEditProfile: ConstraintLayout
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var ivProfile: ImageView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)

        btnLogout = view.findViewById(R.id.btn_logout)
        btnEditProfile = view.findViewById(R.id.editProfile)
        tvName = view.findViewById(R.id.tv_nameProfil)
        tvEmail = view.findViewById(R.id.tv_email)
        ivProfile = view.findViewById(R.id.iv_profil)

        btnLogout.setOnClickListener{
            firebaseAuth.signOut()

            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        btnEditProfile.setOnClickListener{
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        return view
    }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val email = "${snapshot.child("email").value}"
                    val name = "${snapshot.child("name").value}"
                    val number = "${snapshot.child("nomor").value}"
                    val domisili = "${snapshot.child("domisili").value}"
                    val sekolah = "${snapshot.child("sekolah").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    var uid = "${snapshot.child("uid").value}"

                    tvName.text = name
                    tvEmail.text = email

                    try {
                        Glide
                            .with(this@ProfileFragment)
                            .load(profileImage)
                            .placeholder(R.drawable.profil_user)
                            .into(ivProfile)
                    } catch (e: Exception){

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

}