package ru.tasker.example.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.tasker.example.model.Role

@Repository
interface RoleRepository : CrudRepository<Role, Int> {
  fun findOneByName(name: String): Role?
}