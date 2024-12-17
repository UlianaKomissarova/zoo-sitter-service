package dev.uliana.zoo_sitter_service.user.service

import dev.uliana.zoo_sitter_service.user.dto.CreateUserDto
import dev.uliana.zoo_sitter_service.user.dto.UpdateUserDto
import dev.uliana.zoo_sitter_service.user.dto.UserResponseDto
import dev.uliana.zoo_sitter_service.user.repository.UserRepository
import dev.uliana.zoo_sitter_service.util.exception.UserNotFoundException
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
        private val repository: UserRepository,
        private val mapper: UserMapper
) {
    @Transactional(readOnly = true)
    fun getAll(): List<UserResponseDto> = repository.findAllWithRoles()
            .map { user -> mapper.toDto(user) }

    @Transactional(readOnly = true)
    fun getById(id: UUID): UserResponseDto {
        val user = repository.findById(id)
                .orElseThrow { UserNotFoundException("Пользователь с id $id не найден.") }
        return mapper.toDto(user)
    }

    @Transactional
    fun create(dto: CreateUserDto): UserResponseDto {
        val user = repository.save(mapper.toUser(dto))
        return mapper.toDto(user)
    }

    @Transactional
    fun deleteById(id: UUID) {
        if (repository.existsById(id)) {
            repository.deleteById(id)
        } else throw UserNotFoundException("Пользователь с id $id не найден.")
    }

    @Transactional
    fun updateById(id: UUID, dto: UpdateUserDto): UserResponseDto {
        val updated = repository.findById(id)
                .map {
                    mapper.update(dto, it)
                    return@map repository.save(it)
                }
                .orElseThrow { UserNotFoundException("Пользователь с id $id не найден.") }

        return mapper.toDto(updated)
    }
}