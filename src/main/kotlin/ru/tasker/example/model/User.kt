package ru.tasker.example.model

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "user")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int,
    @Size(message="Хуита какая-то", min = 3)
    val username: String,
    var password: String,
    @Transient val passwordConfirm: String,
    val name: String?,
    val birthdate: String?,
    @ManyToMany @JoinTable(name = "user_role", joinColumns = arrayOf(JoinColumn(name = "user_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "role_id")))
    var roles: Set<Role?>?) {
  override fun toString(): String {
    return "username: $username${if (!name.isNullOrBlank()) ", name: $name" else ""}${if (!birthdate.isNullOrBlank()) ", birthdate: $birthdate" else ""}, roles: $roles"
  }
}