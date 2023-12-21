package com.example.edupath.view.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.edupath.R
import com.example.edupath.data.pref.Banner

class ListBannerAdapter(val listBanner: ArrayList<Banner>) : RecyclerView.Adapter<ListBannerAdapter.listBannerHolder>() {

    class listBannerHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_titleBanner)
        val tvDetail: TextView = itemView.findViewById(R.id.tv_detailBanner)
        val ivPhoto: ImageView = itemView.findViewById(R.id.iv_banner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listBannerHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rv_banner, parent, false)
        return listBannerHolder(view)
    }

    override fun getItemCount(): Int = listBanner.size

    override fun onBindViewHolder(holder: listBannerHolder, position: Int) {
        val (title, detail, photoUrl) = listBanner[position]
        holder.tvTitle.text = title
        holder.tvDetail.text = detail
        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .into(holder.ivPhoto)
    }

}