package dev.uliana.zoo_sitter_service.user.controller

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class PostgresTestContainer {
    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> {
        val postgresDBContainer = PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
                .apply {
                    withDatabaseName("test_db")
                    withUsername("user")
                    withPassword("pass")
                }

        postgresDBContainer.start()

        val mappedPort = postgresDBContainer.getMappedPort(5432)
        var jdbcUrl = postgresDBContainer.jdbcUrl.replace("5432", mappedPort.toString())
        jdbcUrl = jdbcUrl.split("?")[0]

        System.setProperty("SPRING_DATASOURCE_URL", jdbcUrl)
        System.setProperty("SPRING_DATASOURCE_USERNAME", postgresDBContainer.username)
        System.setProperty("SPRING_DATASOURCE_PASSWORD", postgresDBContainer.password)

        return postgresDBContainer
    }
}