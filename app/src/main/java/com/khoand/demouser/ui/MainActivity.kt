package com.khoand.demouser.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.khoand.demouser.data.local.AppDatabase
import com.khoand.demouser.data.remote.api.RetrofitBuilder
import com.khoand.demouser.data.remote.model.SvUser
import com.khoand.demouser.data.repository.LocalUserRepository
import com.khoand.demouser.data.repository.SvUserRepository
import com.khoand.demouser.databinding.ActivityMainBinding
import com.khoand.demouser.utils.DeviceUtil
import com.khoand.demouser.utils.Status

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            LocalUserRepository(
                AppDatabase.getDatabase(applicationContext)
            ),
            SvUserRepository(RetrofitBuilder.apiService)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        observerData()
    }

    private fun initView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        binding.rvUser.adapter = adapter
    }

    private fun observerData() {
        if (!DeviceUtil.isConnected(this)) {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.fetchData()
        viewModel.users.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.rvUser.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    binding.rvUser.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    displayDataAndSaveLocal(it.data ?: arrayListOf())
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvUser.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun displayDataAndSaveLocal(listUser: List<SvUser>) {
        adapter.updateData(listUser)
        viewModel.insertDataToDb(listUser)
    }
}