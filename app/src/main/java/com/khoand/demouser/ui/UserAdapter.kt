package com.khoand.demouser.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khoand.demouser.data.remote.model.SvUser
import com.khoand.demouser.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private val dataSet: MutableList<SvUser> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindView(dataSet[position])

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(listSvUser : List<SvUser>){
        dataSet.clear()
        dataSet.addAll(listSvUser)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: ItemUserBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding: ItemUserBinding?

        init {
            binding = itemView
        }

        fun bindView(svUser: SvUser) {
            binding?.tvUserName?.text = svUser.name
            binding?.tvUserEmail?.text = svUser.email
            binding?.imgAvatar?.let { Glide.with(itemView.context).load(svUser.avatar).into(it) }
        }
    }
}