package dev.uliana.zoo_sitter_service.user.dto

import com.fasterxml.jackson.annotation.JsonFormat
import dev.uliana.zoo_sitter_service.common.RegexConstants
import dev.uliana.zoo_sitter_service.common.UtilConstants
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class UpdateUserDto(
        @field:Size(min = 1, max = 100)
        @field:Pattern(regexp = RegexConstants.NAME_REGEX)
        var firstName: String?,

        @field:Size(min = 1, max = 100)
        @field:Pattern(regexp = RegexConstants.NAME_REGEX)
        var lastName: String?,

        @field:Size(min = 1, max = 100)
        @field:Pattern(regexp = RegexConstants.LOGIN_REGEX)
        var userLogin: String?,

        @field:Pattern(regexp = RegexConstants.PASSWORD_REGEX)
        var userPassword: String?,

        @field:Email
        var email: String?,

        @field:Past
        @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UtilConstants.DATE_CONSTANT)
        var userBirthdate: LocalDate?,

        var roles: List<String>?
)
