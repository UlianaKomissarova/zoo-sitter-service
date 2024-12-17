package dev.uliana.zoo_sitter_service.user.service

import dev.uliana.zoo_sitter_service.user.dto.CreateUserDto
import dev.uliana.zoo_sitter_service.user.dto.UpdateUserDto
import dev.uliana.zoo_sitter_service.user.dto.UserResponseDto
import dev.uliana.zoo_sitter_service.user.entity.RoleType
import dev.uliana.zoo_sitter_service.user.entity.User
import java.time.LocalDate
import java.util.UUID

object UserData {
    fun getUserResponseDto(): UserResponseDto {
        return UserResponseDto(
                "Test",
                "Test",
                "test",
                "Test123=",
                "test@mail.com",
                LocalDate.of(2000, 11, 11),
                listOf("USER"))
    }

    fun getUser(): User {
        val userId: UUID = UUID.randomUUID()

        return User(
                userId,
                "Test",
                "Test",
                "test",
                "Test123=",
                "test@mail.com",
                LocalDate.of(2000, 11, 11),
                mutableSetOf(RoleType.USER))
    }

    fun getUserDtoForCreate(): CreateUserDto {
        return CreateUserDto(
                "Test",
                "Test",
                "test",
                "Test123=",
                "test@mail.com",
                LocalDate.of(2000, 11, 11),
                listOf("USER")
        )
    }

    fun getUserDtoForUpdate(): UpdateUserDto {
        return UpdateUserDto(
                "Updated",
                "Update",
                "update",
                "Test123=",
                "update@mail.com",
                LocalDate.of(2000, 11, 11),
                listOf("USER")
        )
    }

    fun getUpdatedUserResponse(): UserResponseDto {
        return UserResponseDto(
                "Updated",
                "Update",
                "update",
                "Test123=",
                "update@mail.com",
                LocalDate.of(2000, 11, 11),
                listOf("USER")
        )
    }
}