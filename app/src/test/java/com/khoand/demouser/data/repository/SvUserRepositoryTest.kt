package com.khoand.demouser.data.repository

import com.khoand.demouser.data.remote.api.ApiService
import com.khoand.demouser.data.remote.model.SvUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk

class SvUserRepositoryTest{
    private lateinit var repository: SvUserRepository
    private val apiService: ApiService = mockk()

    @Before
    fun setUp() {
        repository = SvUserRepository(apiService)
    }

    @Test
    fun `test getUsersFromServer with empty list`() = runBlocking {
        coEvery { apiService.getUsers() } returns listOf()
        val result = repository.getUsersFromSever()

        assertEquals(listOf<SvUser>(), result)
    }

    @Test
    fun `test getUsersFromServer with list of users`() = runBlocking {
        val mockUsers = listOf(
            SvUser(1, "User 1"),
            SvUser(2, "User 2")
        )
        coEvery { apiService.getUsers() } returns mockUsers

        val result = repository.getUsersFromSever()

        assertEquals(mockUsers, result)
    }
}