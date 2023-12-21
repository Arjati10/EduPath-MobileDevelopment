package com.example.edupath.view.main.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.edupath.R
import com.example.edupath.data.pref.Community

class ListCommunityAdapter (val listCommunity: ArrayList<Community>) : RecyclerView.Adapter<ListCommunityAdapter.listCommunityHolder>() {

    class listCommunityHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val namaCommunity: TextView = itemView.findViewById(R.id.tv_titleBelajar)
        val lokasi: TextView = itemView.findViewById(R.id.tv_locationRv)
        val type: TextView = itemView.findViewById(R.id.tv_typeRv)
        val ivGroup: ImageView = itemView.findViewById(R.id.iv_communityPicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listCommunityHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rv_community, parent, false)
        return ListCommunityAdapter.listCommunityHolder(view)
    }

    override fun onBindViewHolder(holder: listCommunityHolder, position: Int) {
        val community = listCommunity[position]

        holder.namaCommunity.text = community.namaCommunity
        holder.lokasi.text = community.lokasi
        holder.type.text = community.type

        val photoGroup = community.photoGroup

        if (photoGroup != null) {
            Glide.with(holder.itemView.context).load(photoGroup).into(holder.ivGroup)
        } else {
            // Set default image if photoGroup is empty
            holder.ivGroup.setImageResource(R.drawable.community_banner)
        }
    }

    override fun getItemCount(): Int = listCommunity.size

}

