package dev.uliana.zoo_sitter_service

import dev.uliana.zoo_sitter_service.user.controller.PostgresTestContainer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Import(PostgresTestContainer::class)
@Testcontainers
class ZooSitterServiceApplicationTests {
	@Test
	fun contextLoads() {
	}
}