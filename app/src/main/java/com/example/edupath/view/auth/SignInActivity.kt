    package com.example.edupath.view.auth

    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.Toast
    import com.example.edupath.databinding.ActivitySignInBinding
    import com.example.edupath.view.main.MainActivity
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.DatabaseReference
    import com.google.firebase.database.FirebaseDatabase

    class SignInActivity : AppCompatActivity() {

        private lateinit var binding: ActivitySignInBinding
        private lateinit var firebaseAuth: FirebaseAuth
        private lateinit var databaseReference: DatabaseReference

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivitySignInBinding.inflate(layoutInflater)
            setContentView(binding.root)

            firebaseAuth = FirebaseAuth.getInstance()
            binding.tvSignup.setOnClickListener{
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }

            binding.btnSignin.setOnClickListener{
                val email = binding.emailEditText.text.toString()
                val pass = binding.passwordEditText.text.toString()

                if (email.isNotEmpty() && pass.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Gagal masuk ${it.exception.toString()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Terdapat kolom yang belum terisi!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        override fun onStart() {
            super.onStart()

            if (firebaseAuth.currentUser != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }