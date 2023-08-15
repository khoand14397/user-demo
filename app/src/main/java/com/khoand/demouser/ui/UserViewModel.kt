package com.khoand.demouser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoand.demouser.data.local.entity.User
import com.khoand.demouser.data.remote.api.RetrofitBuilder
import com.khoand.demouser.data.remote.model.SvUser
import com.khoand.demouser.data.repository.LocalUserRepository
import com.khoand.demouser.data.repository.SvUserRepository
import com.khoand.demouser.utils.StateData
import com.khoand.demouser.utils.svUserToUserLocal
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: LocalUserRepository,
    private val svUserRepository: SvUserRepository
) : ViewModel() {
    private val _users = MutableLiveData<StateData<List<SvUser>>>()
    val users: LiveData<StateData<List<SvUser>>> get() = _users

    fun fetchData() {
        viewModelScope.launch {
            _users.value = StateData.loading()
            try {
                _users.value = StateData.success(svUserRepository.getUsersFromSever())
            } catch (e: Exception) {
                _users.value = StateData.error(e.toString())
            }
        }
    }

    fun insertDataToDb(listUser: List<SvUser>) {
        viewModelScope.launch {
            val listUserLocal = arrayListOf<User>()
            listUser.forEach { user ->
                listUserLocal.add(svUserToUserLocal(user))
            }
            userRepository.insertAll(listUserLocal)
        }
    }
}