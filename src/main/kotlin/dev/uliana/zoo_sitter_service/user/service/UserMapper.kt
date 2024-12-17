package dev.uliana.zoo_sitter_service.user.service

import dev.uliana.zoo_sitter_service.user.dto.CreateUserDto
import dev.uliana.zoo_sitter_service.user.dto.UpdateUserDto
import dev.uliana.zoo_sitter_service.user.dto.UserResponseDto
import dev.uliana.zoo_sitter_service.user.entity.RoleType
import dev.uliana.zoo_sitter_service.user.entity.User
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.Named
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun update(dto: UpdateUserDto, @MappingTarget user: User)

    @Mapping(target = "roles", source = "roles", qualifiedByName = ["mapRoles"])
    fun toUser(dto: CreateUserDto): User

    @Mapping(target = "roles", source = "roles", qualifiedByName = ["mapRolesToStrings"])
    fun toDto(user: User): UserResponseDto

    @Named("mapRoles")
    fun mapRoles(roles: List<String>): MutableSet<RoleType> {
        return roles.map { RoleType.valueOf(it) }.toMutableSet()
    }

    @Named("mapRolesToStrings")
    fun mapRolesToStrings(roles: Set<RoleType>): List<String> {
        return roles.map { it.name }
    }
}