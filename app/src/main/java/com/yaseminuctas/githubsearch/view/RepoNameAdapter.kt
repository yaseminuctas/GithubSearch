package com.yaseminuctas.githubsearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yaseminuctas.githubsearch.R
import com.yaseminuctas.githubsearch.model.RepoNameResponse

class RepoNameAdapter : RecyclerView.Adapter<RepoNameAdapter.ViewHolder>() {

    var list: List<RepoNameResponse.ItemRepoName> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoNameAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_repo_name, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RepoNameAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun getItemCount(): Int {
        return list.size

    }

    fun setRepoList(dataList: List<RepoNameResponse.ItemRepoName>) {
        list = emptyList()
        list = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: RepoNameResponse.ItemRepoName) {
            val tvName = itemView.findViewById(R.id.tvName) as TextView
            val tvDesc  = itemView.findViewById(R.id.tvDesc) as TextView
            tvName.text = user.name
            tvDesc.text = user.description

        }
    }
}

