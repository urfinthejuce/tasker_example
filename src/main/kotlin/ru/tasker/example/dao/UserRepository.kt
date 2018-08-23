package ru.tasker.example.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.tasker.example.model.User

@Repository
interface UserRepository : CrudRepository<User, Int> {
  fun findOneByUsername(username: String): User?
}