package dev.uliana.zoo_sitter_service.user.repository

import dev.uliana.zoo_sitter_service.user.controller.PostgresTestContainer
import dev.uliana.zoo_sitter_service.user.service.UserData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DataJpaTest
@Import(PostgresTestContainer::class)
@Testcontainers
class UserRepositoryTest {
    @Autowired
    private lateinit var repository: UserRepository

    @BeforeEach
    fun cleanDb() {
        repository.deleteAll()
    }

    @Test
    fun findAllWithRolesSuccess() {
        val user = UserData.getUser()
        user.id = null
        repository.save(user)

        val foundUsers = repository.findAllWithRoles()

        assertEquals(1, foundUsers.size)
        assertNotNull(foundUsers[0].roles)
    }
}