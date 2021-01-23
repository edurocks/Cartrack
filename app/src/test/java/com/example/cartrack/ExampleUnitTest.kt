package com.example.cartrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cartrack.model.UsersResponse
import com.example.cartrack.repository.LoginRepository
import com.example.cartrack.repository.Resource
import com.example.cartrack.repository.UsersRepository
import com.example.cartrack.ui.main.UsersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var userRepository: UsersRepository

    @Mock
    private lateinit var loginRepository : LoginRepository

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<List<UsersResponse>>>

    @Before
    fun setUp() {}

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            val viewModel = UsersViewModel(userRepository, loginRepository)
            viewModel.getUsers()
            viewModel.mUsersResponse.observeForever(apiUsersObserver)
            verify(userRepository).getUsers()
            verify(apiUsersObserver).onChanged(Resource.success(userRepository.getUsers()))
            viewModel.mUsersResponse.removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val viewModel = UsersViewModel(userRepository, loginRepository)
            viewModel.getUsers()
            viewModel.mUsersResponse.observeForever(apiUsersObserver)
            verify(userRepository).getUsers()
            verify(apiUsersObserver).onChanged(
                Resource.error(null, "errorMessage")
            )
            viewModel.mUsersResponse.removeObserver(apiUsersObserver)
        }
    }

    @After
    fun tearDown() {}

}