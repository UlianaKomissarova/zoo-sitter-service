package dev.uliana.zoo_sitter_service.user.service

import dev.uliana.zoo_sitter_service.user.dto.UserResponseDto
import dev.uliana.zoo_sitter_service.user.entity.User
import dev.uliana.zoo_sitter_service.user.repository.UserRepository
import dev.uliana.zoo_sitter_service.util.exception.UserNotFoundException
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import java.util.Optional
import java.util.UUID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockKExtension::class)
class UserServiceTest {
    private val mapper: UserMapper = mockk()

    private val repository: UserRepository = mockk()

    private val service: UserService = UserService(repository, mapper)

    @BeforeEach
    fun setUp() {
        clearMocks(mapper, repository)
    }

    @Test
    fun getAllSuccess() {
        val user: UserResponseDto = UserData.getUserResponseDto()
        val users = listOf(user)

        every { repository.findAllWithRoles() } returns listOf(UserData.getUser())
        every { mapper.toDto(any()) } returns user

        val foundUsers = service.getAll()

        assertEquals(users.size, foundUsers.size)
        assertEquals(users[0], foundUsers[0])
        verify { repository.findAllWithRoles() }
        verify { mapper.toDto(any()) }
    }

    @Test
    fun getAllEmptyList() {
        val user: UserResponseDto = UserData.getUserResponseDto()
        val users = emptyList<UserResponseDto>()

        every { repository.findAllWithRoles() } returns emptyList()

        val foundUsers = service.getAll()

        assertEquals(0, foundUsers.size)
        verify { repository.findAllWithRoles() }
    }

    @Test
    fun getByIdSuccess() {
        val user: User = UserData.getUser()
        val dto: UserResponseDto = UserData.getUserResponseDto()

        every { repository.findById(any()) } returns Optional.of(user)
        every { mapper.toDto(any()) } returns dto

        val foundUser = service.getById(user.id!!)

        assertNotNull(foundUser)
        verify { repository.findById(any()) }
        verify { mapper.toDto(any()) }

    }

    @Test
    fun getByIdThrowsNotFound() {
        val randomId: UUID = UUID.randomUUID()

        every { repository.findById(any()) } throws UserNotFoundException("Пользователь с id $randomId не найден.")

        assertThrows<UserNotFoundException> { service.getById(randomId) }
        verify { repository.findById(any()) }
    }

    @Test
    fun deleteByIdSuccess() {
        val user: User = UserData.getUser()

        every { repository.existsById(any()) } returns true
        every { repository.deleteById(any()) } just Runs

        service.deleteById(user.id!!)

        verify { repository.existsById(any()) }
        verify { repository.deleteById(any()) }
    }

    @Test
    fun deleteByIdThrowsNotFound() {
        val randomId = UUID.randomUUID()

        every { repository.existsById(any()) } returns false
        every { repository.deleteById(any()) } throws UserNotFoundException("Пользователь с id $randomId не найден.")

        assertThrows<UserNotFoundException> { service.deleteById(randomId) }
        verify { repository.existsById(any()) }
    }

    @Test
    fun saveSuccess() {
        val dto = UserData.getUserDtoForCreate()
        val user = UserData.getUser()
        val response = UserData.getUserResponseDto()

        every { mapper.toUser(dto) } returns user
        every { repository.save(user) } returns user
        every { mapper.toDto(user) } returns response

        val result = service.create(dto)

        assertNotNull(result)
        assertEquals(response, result)
        verify { mapper.toUser(dto) }
        verify { repository.save(user) }
        verify { mapper.toDto(user) }
    }

    @Test
    fun updateByIdSuccess() {
        val dto = UserData.getUserDtoForUpdate()
        val user = UserData.getUser()
        val response = UserData.getUpdatedUserResponse()

        every { repository.findById(user.id!!) } returns Optional.of(user)
        every { mapper.update(dto, user) } just Runs
        every { repository.save(user) } returns user
        every { mapper.toDto(user) } returns response

        val updated = service.updateById(user.id!!, dto)

        assertNotNull(updated)
        assertEquals(response, updated)
        verify { repository.findById(user.id!!) }
        verify { mapper.update(dto, user) }
        verify { repository.save(user) }
        verify { mapper.toDto(user) }
    }

    @Test
    fun updateByIdThrowsNotFound() {
        val randomId = UUID.randomUUID()
        val dto = UserData.getUserDtoForUpdate()

        every { repository.findById(randomId) } throws UserNotFoundException("Пользователь с id $randomId не найден.")

        assertThrows<UserNotFoundException> { service.updateById(randomId, dto) }
        verify { repository.findById(randomId) }
    }
}