package com.hendyapp.githubuserkompas.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hendyapp.githubuserkompas.R
import com.hendyapp.githubuserkompas.databinding.ItemUserBinding
import com.hendyapp.githubuserkompas.model.GithubUser

class MainAdapter(private val context: Context): ListAdapter<GithubUser, MainAdapter.MainViewHolder>(DiffCallback()) {
    private class DiffCallback: DiffUtil.ItemCallback<GithubUser>() {
        override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean
            = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean
            = oldItem == newItem
    }

    private lateinit var onItemClick: (GithubUser) -> Unit

    fun setOnItemClick(onItemClick: (GithubUser) -> Unit) {
        this.onItemClick = onItemClick
    }

    inner class MainViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(githubUser: GithubUser) {
            binding.itemUserLogin.text = "@${githubUser.login}"
            Glide.with(binding.itemUserImage)
                .load(githubUser.avatar)
                .placeholder(R.drawable.ic_baseline_person_24)
                .circleCrop()
                .into(binding.itemUserImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder
        = MainViewHolder(ItemUserBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            onItemClick(currentList[position])
        }
    }
}