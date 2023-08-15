package com.khoand.demouser.ui

import androidx.arch.core.executor.DefaultTaskExecutor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.khoand.demouser.BaseTest
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
import io.mockk.mockkConstructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class UserViewModelTest : BaseTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var svUserRepository: SvUserRepository

    @MockK
    private lateinit var userRepository: LocalUserRepository

    @InjectMockKs
    private lateinit var viewModel: UserViewModel

    override fun before() {
        super.before()
        MockKAnnotations.init(this)
        mockkConstructor(DefaultTaskExecutor::class)
        coEvery {
            anyConstructed<DefaultTaskExecutor>().isMainThread
        } returns true
    }

    @Test
    fun `test fetchData success`() = runTest {
        // Arrange
        val listUser = listOf(SvUser(1, "John", "john@example.com", "avatar_url"))
        coEvery { svUserRepository.getUsersFromSever() } returns listUser

        // Act
        viewModel.fetchData()

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
        
        // Assert
        coVerify {
            userRepository.insertAll(localUsers)
        }
    }
}