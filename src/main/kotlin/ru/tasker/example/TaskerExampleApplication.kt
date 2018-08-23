package ru.tasker.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@SpringBootApplication
class TaskerExampleApplication() {
  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

  @Bean
  fun messageSource(): ResourceBundleMessageSource {
    val ms = ResourceBundleMessageSource()
    ms.setBasename("classpath:validation")
    return ms
  }
}

  fun main(args: Array<String>) {
    runApplication<TaskerExampleApplication>(*args)
  }



