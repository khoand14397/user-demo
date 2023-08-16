package com.khoand.demouser.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.khoand.demouser.MainCoroutineRule
import com.khoand.demouser.data.remote.model.SvUser
import com.khoand.demouser.data.repository.LocalUserRepository
import com.khoand.demouser.data.repository.SvUserRepository
import com.khoand.demouser.utils.StateData
import com.khoand.demouser.utils.svUserToUserLocal
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class UserViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var svUserRepository: SvUserRepository

    @MockK
    private lateinit var userRepository: LocalUserRepository

    @InjectMockKs
    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun `test fetchData success`() = runTest {
        // Arrange
        val listUser = listOf(SvUser(1, "John", "john@example.com", "avatar_url"))
        coEvery { svUserRepository.getUsersFromSever() } returns listUser

        // Act
        viewModel.fetchData()
        advanceUntilIdle()

        // Assert
        coVerify { svUserRepository.getUsersFromSever() }
        assertEquals(StateData.success(listUser), viewModel.users.value)
    }

    @Test
    fun `test fetchData error`() = runTest {
        // Arrange
        val errorMessage = RuntimeException("Network error")
        coEvery { svUserRepository.getUsersFromSever() } throws errorMessage

        // Act
        viewModel.fetchData()
        advanceUntilIdle()

        // Assert
        assertEquals(
            StateData.error("java.lang.RuntimeException: Network error"),
            viewModel.users.value
        )
    }

    @Test
    fun `test insertDataToDb success with list user`() = runTest {
        // Arrange
        val svUsers = listOf(SvUser(1, "John", "john@example.com", "avatar_url"))
        val localUsers = svUsers.map { svUserToUserLocal(it) }

        // Act
        viewModel.insertDataToDb(svUsers)
        advanceUntilIdle()

        // Assert
        coVerify {
            userRepository.insertAll(localUsers)
        }
    }
}