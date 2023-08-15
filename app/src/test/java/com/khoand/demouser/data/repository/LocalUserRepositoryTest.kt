package com.khoand.demouser.data.repository

import com.khoand.demouser.data.local.AppDatabase
import com.khoand.demouser.data.local.dao.UserDao
import com.khoand.demouser.data.local.entity.User
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocalUserRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var localUserRepository: LocalUserRepository

    @Before
    fun setup() {
        database = mockk()
        userDao = mockk()
        every { database.userDao() } returns userDao
        localUserRepository = LocalUserRepository(database)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `insertAll should call userDao insertAll`() = runBlocking {
        // Arrange
        val userList = listOf(
            User(1, "user 1", "user1@abc.com", "avatar.jpg"),
            User(2, "user 2", "user2@abc.com", "avatar.jpg")
        )

        coEvery { userDao.insertAll(userList) } just Runs

        // Act
        localUserRepository.insertAll(userList)

        // Assert
        coVerify { userDao.insertAll(userList) }
    }
}