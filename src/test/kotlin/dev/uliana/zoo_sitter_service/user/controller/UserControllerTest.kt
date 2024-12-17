package dev.uliana.zoo_sitter_service.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.uliana.zoo_sitter_service.user.repository.UserRepository
import dev.uliana.zoo_sitter_service.user.service.UserData
import dev.uliana.zoo_sitter_service.user.service.UserService
import java.time.LocalDate
import java.util.UUID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresTestContainer::class)
@Testcontainers
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var service: UserService

    @Autowired
    private lateinit var repository: UserRepository

    @BeforeEach
    @Transactional
    fun cleanDb() {
        repository.deleteAll()
    }

    @Test
    fun getAllOk() {
        service.create(UserData.getUserDtoForCreate())

        mockMvc.perform(MockMvcRequestBuilders.get(("/api/v1/users"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$[0].firstName").value("Test"))
                .andExpect(jsonPath("\$[0].email").value("test@mail.com"))
    }

    @Test
    fun getByIdOk() {
        val user = UserData.getUser()
        user.id = null;
        val savedUser = repository.save(user)

        mockMvc.perform(MockMvcRequestBuilders.get(("/api/v1/users/" + savedUser.id))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.email").value("test@mail.com"))
    }

    @Test
    fun getByInvalidId() {
        mockMvc.perform(MockMvcRequestBuilders.get(("/api/v1/users/1"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun getByIdNotFound() {
        val nonExistentId = UUID.randomUUID()

        mockMvc.perform(MockMvcRequestBuilders.get(("/api/v1/users/$nonExistentId"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun deleteByIdNoContent() {
        val user = UserData.getUser()
        user.id = null;
        val savedUser = repository.save(user)

        mockMvc.perform(MockMvcRequestBuilders.delete(("/api/v1/users/" + savedUser.id))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
    }

    @Test
    fun deleteByInvalidId() {
        mockMvc.perform(MockMvcRequestBuilders.delete(("/api/v1/users/1"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun deleteByIdNotFound() {
        val nonExistentId = UUID.randomUUID()

        mockMvc.perform(MockMvcRequestBuilders.delete(("/api/v1/users/$nonExistentId"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun createSuccess() {
        val requestBody = UserData.getUserDtoForCreate()

        mockMvc.perform(MockMvcRequestBuilders.post(("/api/v1/users"))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.email").value("test@mail.com"))
    }

    @Test
    fun createEmailNotValid() {
        val requestBody = UserData.getUserDtoForCreate()
        requestBody.email = "not-valid"

        mockMvc.perform(MockMvcRequestBuilders.post(("/api/v1/users"))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun createNameNotValid() {
        val requestBody = UserData.getUserDtoForCreate()
        requestBody.firstName = "not-valid"

        mockMvc.perform(MockMvcRequestBuilders.post(("/api/v1/users"))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun updateSuccess() {
        val user = UserData.getUser()
        user.id = null;
        val savedUser = repository.save(user)
        val requestBody = UserData.getUserDtoForUpdate()

        mockMvc.perform(MockMvcRequestBuilders.patch(("/api/v1/users/" + savedUser.id))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.email").value("update@mail.com"))
    }

    @Test
    fun updateBirthdateNotValid() {
        val user = UserData.getUser()
        user.id = null;
        val savedUser = repository.save(user)
        val requestBody = UserData.getUserDtoForUpdate()
        requestBody.userBirthdate = LocalDate.of(3030, 11, 11)

        mockMvc.perform(MockMvcRequestBuilders.patch(("/api/v1/users/" + savedUser.id))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }
}