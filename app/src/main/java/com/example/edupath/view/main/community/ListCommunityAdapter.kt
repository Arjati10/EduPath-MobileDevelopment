package com.example.edupath.view.main.community

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.edupath.R
import com.example.edupath.data.pref.Community
import com.example.edupath.databinding.RvCommunityBinding

class ListCommunityAdapter (val listCommunity: ArrayList<Community>) : RecyclerView.Adapter<ListCommunityAdapter.listCommunityHolder>() {

    class listCommunityHolder(val binding: RvCommunityBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(community: Community) {
            itemView.context
            with(binding) {
                tvLocationRv.text = community.lokasi
                tvTitleBelajar.text = community.namaCommunity
                tvTypeRv.text = community.type
                Glide.with(itemView.context).load(community.photoGroup).into(ivCommunityPicture)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listCommunityHolder {
        return listCommunityHolder(
            RvCommunityBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: listCommunityHolder, position: Int) {
        val community = listCommunity[position]
        holder.bind(community)

        val context = holder.itemView.context
        holder.binding.btnJoin.setOnClickListener {
            context.startActivity(
                Intent(context, DetailCommunityActivity::class.java).also {
                    it.putExtra("communityId", community)
                }
            )
        }
    }

    override fun getItemCount(): Int = listCommunity.size

}

