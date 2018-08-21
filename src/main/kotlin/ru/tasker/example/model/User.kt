package ru.tasker.example.model

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int? = null,
    val username: String? = null,
    val password: String? = null,
    @Transient val passwordConfirm: String? = null,
    val name: String? = null,
    val birthdate: String? = null,
    @ManyToMany @JoinTable(name="user_role", joinColumns = arrayOf(JoinColumn(name = "user_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "role_id")))
    val roles: Set<Role>? = null ) {
  override fun toString(): String {
    return "username: $username, name: $name${if (!birthdate.isNullOrBlank()) ", birthdate: $birthdate" else ""}, roles: $roles"
  }
}