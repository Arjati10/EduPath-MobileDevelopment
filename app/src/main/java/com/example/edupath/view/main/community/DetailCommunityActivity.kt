package com.example.edupath.view.main.community

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.edupath.R
import com.example.edupath.data.pref.Community
import com.example.edupath.databinding.ActivityDetailCommunityBinding
import com.example.edupath.view.glide

class DetailCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCommunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("communityId", Community::class.java)
        } else {
            intent.getParcelableExtra("communityId")
        }

        data?.let {  community ->
            with(binding) {
                tvTitle.text = community.namaCommunity
                tvDetail.text = community.detail
                tvLocation.text = community.lokasi
                tvType.text = community.type
                ivGroup.glide(community.photoGroup)
            }
        }
    }
}