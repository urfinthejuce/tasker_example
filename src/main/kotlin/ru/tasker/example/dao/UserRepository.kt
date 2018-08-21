package ru.tasker.example.dao

import org.springframework.data.repository.CrudRepository
import ru.tasker.example.model.User

interface UserRepository : CrudRepository<User, Int> {
  fun findOneByUsername(username: String?): User?
}