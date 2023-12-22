package com.example.edupath.view.tesminatbakat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.edupath.R
import com.example.edupath.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResultFragment : Fragment() {

    private lateinit var tvJurusan: TextView
    private lateinit var tvName: TextView
    private lateinit var tvUcapan: TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        tvJurusan = view.findViewById(R.id.tv_jurusan)
        tvName = view.findViewById(R.id.tv_selamatNama)
        tvUcapan = view.findViewById(R.id.tv_ucapan)

        val btnBack = view.findViewById<Button>(R.id.btn_back)
        val btnDetail = view.findViewById<Button>(R.id.btn_detailJurusan)

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        val bundle = arguments
        val jurusan = bundle?.getString("predicted_class")

        if (jurusan != null){
            tvJurusan.text = jurusan
            tvUcapan.text = "kami dengan bangga merekomendasikan kamu untuk bergabung dengan program studi ${jurusan}. Rekomendasi ini didasarkan pada deskripsi yang kamu masukkan mengenai diri kamu. Kami yakin, program studi ini akan menjadi tempat yang tepat untukmu mengembangkan potensi diri, meraih cita-cita, dan sukses di masa depan."
        } else {
            Toast.makeText(requireContext(), "Kosong Bang", Toast.LENGTH_SHORT).show()
        }

        btnBack.setOnClickListener{
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        btnDetail.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.google.com/search?q=${jurusan}")
            startActivity(intent)
        }

        return view
    }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("name").value}"

                    tvName.text = "Selamat ${name}"
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}