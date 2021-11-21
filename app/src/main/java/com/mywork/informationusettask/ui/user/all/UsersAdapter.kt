package com.mywork.informationusettask.ui.user.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mywork.informationusettask.R
import com.mywork.informationusettask.databinding.UserRowBinding
import com.mywork.informationusettask.model.entity.User
import com.mywork.informationusettask.ui.user.proifle.MyProfileFragment
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class UsersAdapter @Inject constructor() :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    private var listUsers:List<User> = emptyList()

    fun setListUsers( listUsers:List<User>){
        this.listUsers = listUsers
        notifyDataSetChanged()
    }


    inner class UserViewHolder(val binding: UserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.deleteUser.setOnClickListener {
                onItemClickListener.clickDelete(listUsers[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view:UserRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.user_row, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = listUsers[position]
        holder.binding.user = user
        holder.binding.isAdmin = MyProfileFragment.isUserLoggedAdmin
        holder.binding.executePendingBindings()
    }

    private lateinit var onItemClickListener: ClickListener

    fun setOnItemClickListener(onItemClickListener: ClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface ClickListener {
        fun clickDelete(user: User)
    }

    override fun getItemCount(): Int {
      return  listUsers.size
    }

}