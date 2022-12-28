package com.example.myapplication.realtime_database.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemUserRtdbListBinding

class ListUserAdapter(private var users: MutableList<User>) :
    RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {
    private lateinit var binding: ItemUserRtdbListBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setUsersData(users: MutableList<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = ItemUserRtdbListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.binding.tvIdUser.text = user.id.toString()
        holder.binding.tvNameUser.text = user.name.toString()
    }

    override fun getItemCount() = users.size

    class UserViewHolder(binding: ItemUserRtdbListBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemUserRtdbListBinding

        init {
            this.binding = binding
        }
    }
}