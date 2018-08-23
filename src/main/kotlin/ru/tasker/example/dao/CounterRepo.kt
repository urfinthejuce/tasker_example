package ru.tasker.example.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.tasker.example.model.UserCounter

@Repository
interface CounterRepo: CrudRepository<UserCounter, Int>