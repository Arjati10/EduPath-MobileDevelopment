package com.example.edupath.view.tesminatbakat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.edupath.R

class TesMinatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tes_minat)

        supportActionBar?.hide()
    }
}