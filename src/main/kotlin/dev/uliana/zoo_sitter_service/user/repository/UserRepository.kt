package dev.uliana.zoo_sitter_service.user.repository

import dev.uliana.zoo_sitter_service.user.entity.User
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, UUID> {
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.roles")
    fun findAllWithRoles(): List<User>
}