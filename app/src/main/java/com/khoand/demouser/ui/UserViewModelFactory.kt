package com.khoand.demouser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khoand.demouser.data.repository.LocalUserRepository
import com.khoand.demouser.data.repository.SvUserRepository

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val userRepository: LocalUserRepository, private val svUserRepository: SvUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UserViewModel(userRepository, svUserRepository) as T
}