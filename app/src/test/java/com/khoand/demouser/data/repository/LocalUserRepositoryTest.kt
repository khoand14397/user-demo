package com.khoand.demouser.data.repository

import com.khoand.demouser.data.local.AppDatabase
import com.khoand.demouser.data.local.entity.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalUserRepositoryTest{
    private lateinit var repository: LocalUserRepository
    private val database: AppDatabase = mockk()

    @Before
    fun setUp() {
        repository = LocalUserRepository(database)
    }

    @Test
    fun `test insertAll all users`() = runBlocking {
        // Arrange
        val mockUsers = listOf(
            User(1, "User 1"),
            User(2, "User 2")
        )

        // Act
        repository.insertAll(mockUsers)

        // Assert
        coEvery { database.userDao().insertAll(mockUsers) } coAnswers { Unit }
    }
}