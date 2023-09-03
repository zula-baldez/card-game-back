package com.example.authservice.database

import com.example.authservice.util.Role
import jakarta.persistence.*
import lombok.Data


@Table(name = "roles")
@Entity
@Data
class RoleEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public val id: Long? = null

    @Column(name = "role_name")
    val roleName: Role? = null
}


