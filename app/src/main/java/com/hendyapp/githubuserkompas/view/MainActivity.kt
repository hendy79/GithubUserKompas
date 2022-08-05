package com.hendyapp.githubuserkompas.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hendyapp.githubuserkompas.databinding.ActivityMainBinding
import com.hendyapp.githubuserkompas.model.GithubUser
import com.hendyapp.githubuserkompas.util.CountingIdlingResourceSingleton
import com.hendyapp.githubuserkompas.view.adapter.MainAdapter
import com.hendyapp.githubuserkompas.viewmodel.MainViewModel
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var moshiUser: JsonAdapter<GithubUser>

    @Inject
    lateinit var adapter: MainAdapter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var dividerItemDecoration: DividerItemDecoration

    @Inject
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.actMainRecycler.layoutManager = layoutManager
        binding.actMainRecycler.adapter = adapter
        binding.actMainRecycler.addItemDecoration(dividerItemDecoration)

        adapter.setOnItemClick { user ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(GithubUser::javaClass.name, moshiUser.toJson(user))
            startActivity(intent)
        }

        viewModel.errorLive.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.usersLive.observe(this) { users ->
            adapter.submitList(users)
            CountingIdlingResourceSingleton.decrement()
        }

        binding.actMainSearch.addTextChangedListener {
            CountingIdlingResourceSingleton.decrement()
            CountingIdlingResourceSingleton.increment()
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({
                val query = it.toString()
                if(query.isNotEmpty())
                    viewModel.getSearchUser(query)
            }, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CountingIdlingResourceSingleton.decrement()
    }
}