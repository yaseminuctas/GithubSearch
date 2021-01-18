package com.yaseminuctas.githubsearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yaseminuctas.githubsearch.R
import com.yaseminuctas.githubsearch.model.UserNameResponse


class UserNameAdapter : RecyclerView.Adapter<UserNameAdapter.ViewHolder>() {

    var list: List<UserNameResponse.ItemUserName> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserNameAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_username, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserNameAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }
    override fun getItemCount(): Int {
        return list.size

    }

    fun setUserList(dataList: List<UserNameResponse.ItemUserName>) {
        list = emptyList()
        list = dataList
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: UserNameResponse.ItemUserName) {
            val tvUserName = itemView.findViewById(R.id.tvUserName) as TextView
            val tvUserUrl  = itemView.findViewById(R.id.tvUserUrl) as TextView
            val imgAvatar = itemView.findViewById(R.id.imgUser) as ImageView
            tvUserName.text = user.login
            tvUserUrl.text = user.htmlUrl

            val media = user.avatarUrl
            if (media.isNullOrEmpty()) {
                imgAvatar.setImageResource(R.drawable.ic_launcher_background)
            } else {
                Glide.with(itemView)
                    .load(media)
                    .into(imgAvatar)
            }
        }
    }
}
