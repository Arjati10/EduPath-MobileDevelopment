package com.example.edupath.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.edupath.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvSudah.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener{
            val email = binding.emailEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            val confirmPass = binding.confirmPassEditText.text.toString()
            val number = ""
            val domisili = ""
            val sekolah = ""
            val profileImage = ""

            if (email.isNotEmpty() && name.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if (pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            val uid = firebaseAuth.currentUser!!.uid

                            val hashmap : HashMap<String, Any> = HashMap()
                            hashmap["name"] = "$name"
                            hashmap["email"] = "$email"
                            hashmap["number"] = "$number"
                            hashmap["domisili"] = "$domisili"
                            hashmap["sekolah"] = "$sekolah"
                            hashmap["profileImage"] = "$profileImage"

                            val ref = FirebaseDatabase.getInstance().getReference("Users")
                            ref.child(firebaseAuth.uid!!)
                                .updateChildren(hashmap)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{
                                    Toast.makeText(this, "Gagal menambahkan data ", Toast.LENGTH_SHORT).show()
                                }

                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password yang Anda masukkan berbeda", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Terdapat kolom yang belum terisi!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}