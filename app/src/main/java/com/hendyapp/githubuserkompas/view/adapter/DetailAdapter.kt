package com.hendyapp.githubuserkompas.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hendyapp.githubuserkompas.databinding.ItemRepoBinding
import com.hendyapp.githubuserkompas.model.GithubRepo
import java.text.SimpleDateFormat
import java.util.*

class DetailAdapter(private val context: Context): ListAdapter<GithubRepo, DetailAdapter.DetailViewHolder>(DiffCallback()) {
    private class DiffCallback: DiffUtil.ItemCallback<GithubRepo>() {
        override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean
            = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean
            = oldItem == newItem
    }

    inner class DetailViewHolder(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(githubRepo: GithubRepo) {
            binding.itemRepoName.text = githubRepo.name
            if(githubRepo.desc != null)
                binding.itemRepoDesc.text = githubRepo.desc
            else
                binding.itemRepoDesc.visibility = View.GONE
            binding.itemRepoStar.text = githubRepo.star.toString()

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val utcTimezone = TimeZone.getTimeZone("UTC")
            TimeZone.setDefault(utcTimezone)
            sdf.timeZone = utcTimezone
            val time = sdf.parse(githubRepo.updated)!!.time
            val now = System.currentTimeMillis()
            val updated = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);

            binding.itemRepoUpdated.text = "Updated $updated"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder
        = DetailViewHolder(ItemRepoBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int)
        = holder.bind(currentList[position])
}