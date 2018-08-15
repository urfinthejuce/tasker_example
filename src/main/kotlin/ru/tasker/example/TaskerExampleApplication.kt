package ru.tasker.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskerExampleApplication

fun main(args: Array<String>) {
  val ac = runApplication<TaskerExampleApplication>(*args)
}
