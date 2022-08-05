package com.hendyapp.githubuserkompas.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hendyapp.githubuserkompas.R
import com.hendyapp.githubuserkompas.databinding.ActivityDetailBinding
import com.hendyapp.githubuserkompas.model.GithubUser
import com.hendyapp.githubuserkompas.view.adapter.DetailAdapter
import com.hendyapp.githubuserkompas.viewmodel.DetailViewModel
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var adapter: DetailAdapter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var dividerItemDecoration: DividerItemDecoration

    @Inject
    lateinit var moshiUser: JsonAdapter<GithubUser>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.actDetailRecycler.layoutManager = layoutManager
        binding.actDetailRecycler.adapter = adapter
        binding.actDetailRecycler.addItemDecoration(dividerItemDecoration)

        val githubUser = moshiUser.fromJson(intent.getStringExtra(GithubUser::javaClass.name)!!)!!

        viewModel.errorLive.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }

        viewModel.reposLive.observe(this) { repos ->
            if(repos.isNotEmpty())
                adapter.submitList(repos)
        }

        viewModel.userLive.observe(this) { user ->
            Glide.with(this)
                .load(user.avatar)
                .placeholder(R.drawable.ic_baseline_person_24)
                .circleCrop()
                .into(binding.actDetailImage)
            binding.actDetailName.text = user.name
            binding.actDetailLogin.text = "@${user.login}"
            if(user.bio != null)
                binding.actDetailBio.text = user.bio
            else
                binding.actDetailBio.visibility = View.GONE
        }

        viewModel.getUserData(githubUser.profile, githubUser.repos)
    }
}