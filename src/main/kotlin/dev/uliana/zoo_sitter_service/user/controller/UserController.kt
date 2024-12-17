package dev.uliana.zoo_sitter_service.user.controller

import dev.uliana.zoo_sitter_service.user.dto.CreateUserDto
import dev.uliana.zoo_sitter_service.user.dto.UpdateUserDto
import dev.uliana.zoo_sitter_service.user.dto.UserResponseDto
import dev.uliana.zoo_sitter_service.user.service.UserService
import jakarta.validation.Valid
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<UserResponseDto>> = ResponseEntity.ok(userService.getAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<UserResponseDto> = ResponseEntity.ok(userService.getById(id))

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateUserDto): ResponseEntity<UserResponseDto> {
        val response = userService.create(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: UUID): ResponseEntity<Void> {
        userService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}")
    fun updateById(@PathVariable id: UUID, @Valid @RequestBody dto: UpdateUserDto): ResponseEntity<UserResponseDto> {
        val response = userService.updateById(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}