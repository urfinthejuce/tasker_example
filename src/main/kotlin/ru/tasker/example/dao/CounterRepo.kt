package ru.tasker.example.dao

import org.springframework.data.repository.CrudRepository
import ru.tasker.example.model.UserCounter

interface CounterRepo: CrudRepository<UserCounter, Int>