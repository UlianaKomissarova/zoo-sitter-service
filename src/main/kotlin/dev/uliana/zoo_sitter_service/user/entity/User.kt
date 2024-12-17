package dev.uliana.zoo_sitter_service.user.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID
import org.hibernate.annotations.ColumnTransformer

@Entity
@Table(name = "app_user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID? = null,

        @Column(nullable = false)
        var firstName: String,

        @Column(nullable = false)
        var lastName: String,

        @Column(nullable = false, unique = true)
        var userLogin: String,

        @Column(nullable = false)
        var userPassword: String,

        @Column(nullable = false, unique = true)
        var email: String,

        @Column(nullable = false)
        var userBirthdate: LocalDate,

        @ElementCollection
        @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        @ColumnTransformer(write = "?::user_role_enum")
        var roles: MutableSet<RoleType> = mutableSetOf()
)