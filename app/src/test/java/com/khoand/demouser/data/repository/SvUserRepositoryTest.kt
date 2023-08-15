package com.khoand.demouser.data.repository

import com.khoand.demouser.data.remote.api.ApiService
import com.khoand.demouser.data.remote.model.SvUser
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SvUserRepositoryTest {
    private lateinit var repository: SvUserRepository
    private val apiService: ApiService = mockk()

    @Before
    fun setUp() {
        repository = SvUserRepository(apiService)
    }

    @Test
    fun `test getUsersFromServer with empty list`() = runBlocking {
        // Arrange
        coEvery { apiService.getUsers() } returns listOf()

        // Act
        val result = repository.getUsersFromSever()

        // Assert
        assertEquals(listOf<SvUser>(), result)
    }

    @Test
    fun `test getUsersFromServer with list of users`() = runBlocking {
        // Arrange
        val mockUsers = listOf(
            SvUser(1, "User 1", "email 1", "avatar 1"),
            SvUser(2, "User 2")
        )
        coEvery { apiService.getUsers() } returns mockUsers

        // Act
        val result = repository.getUsersFromSever()

        // Assert
        assertEquals(mockUsers, result)
    }
}