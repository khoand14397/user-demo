package com.khoand.demouser.data.repository

import com.khoand.demouser.data.local.AppDatabase
import com.khoand.demouser.data.local.entity.User
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class LocalUserRepositoryTest {

    @RelaxedMockK
    private lateinit var database: AppDatabase

    @InjectMockKs
    private lateinit var localUserRepository: LocalUserRepository

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `insertAll should call userDao insertAll`() = runBlocking {
        // Arrange
        val userList = listOf(
            User(1, "user 1", "user1@abc.com", "avatar.jpg"),
            User(2, "user 2", "user2@abc.com", "avatar.jpg")
        )

        // Act
        localUserRepository.insertAll(userList)

        // Assert
        coVerify { database.userDao().insertAll(userList) }
    }
}