package dev.uliana.zoo_sitter_service.user.dto

import com.fasterxml.jackson.annotation.JsonFormat
import dev.uliana.zoo_sitter_service.common.UtilConstants
import java.time.LocalDate

data class UserResponseDto(
        var firstName: String,

        var lastName: String,

        var userLogin: String,

        var userPassword: String,

        var email: String,

        @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UtilConstants.DATE_CONSTANT)
        var userBirthdate: LocalDate,

        var roles: List<String>
)
