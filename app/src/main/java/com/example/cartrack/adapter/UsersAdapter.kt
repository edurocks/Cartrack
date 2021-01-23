package com.example.cartrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.R
import com.example.cartrack.model.UsersResponse

class UsersAdapter (private val usersList: List<UsersResponse>,
                    private val userClickListener: userRowClickListener)
    : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName : AppCompatTextView = itemView.findViewById(R.id.name)
        val userEmail : AppCompatTextView = itemView.findViewById(R.id.email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.users_list_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = usersList[position]
        holder.userName.text = user.name
        holder.userEmail.text = user.email
        holder.itemView.setOnClickListener {
            userClickListener.onUserClickListener(users = user)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    interface userRowClickListener {
        fun onUserClickListener(users: UsersResponse)
    }
}