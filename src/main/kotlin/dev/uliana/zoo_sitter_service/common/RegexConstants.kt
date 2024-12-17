package dev.uliana.zoo_sitter_service.common

object RegexConstants {
    const val NAME_REGEX = "^([А-ЯЁ]{1}[а-яё]{1,99}([-][А-ЯЁа-яё]{1,99})*|[A-Z]{1}[a-z]{1,99}([-][A-Za-z]{1,99})*)$";

    const val LOGIN_REGEX = "^[a-zA-Z0-9._-]{3,99}$";

    const val PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
}