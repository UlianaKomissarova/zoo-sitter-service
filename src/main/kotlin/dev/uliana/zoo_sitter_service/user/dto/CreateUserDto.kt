package dev.uliana.zoo_sitter_service.user.dto

import com.fasterxml.jackson.annotation.JsonFormat
import dev.uliana.zoo_sitter_service.common.RegexConstants
import dev.uliana.zoo_sitter_service.common.UtilConstants
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class CreateUserDto(
        @field:NotBlank
        @field:Size(min = 1, max = 100)
        @field:Pattern(regexp = RegexConstants.NAME_REGEX)
        var firstName: String,

        @field:NotBlank
        @field:Size(min = 1, max = 100)
        @field:Pattern(regexp = RegexConstants.NAME_REGEX)
        var lastName: String,

        @field:NotBlank
        @field:Size(min = 1, max = 100)
        @field:Pattern(regexp = RegexConstants.LOGIN_REGEX)
        var userLogin: String,

        @field:NotBlank
        @field:Pattern(regexp = RegexConstants.PASSWORD_REGEX)
        var userPassword: String,

        @field:NotBlank
        @field:Email
        var email: String,

        @field:NotNull
        @field:Past
        @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UtilConstants.DATE_CONSTANT)
        var userBirthdate: LocalDate,

        @field:NotNull
        var roles: List<String>
)
