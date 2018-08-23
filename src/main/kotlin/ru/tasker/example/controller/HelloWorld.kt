package ru.tasker.example.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import ru.tasker.example.dao.UserRepository
import ru.tasker.example.dao.CounterRepo
import ru.tasker.example.model.UserCounter
import java.util.*

@RestController()
@RequestMapping("/hello")
class HelloController(@Autowired val cntRepo: CounterRepo, @Autowired val userRepo: UserRepository) {
  @GetMapping("")
  fun hello(): ModelAndView {
    val username = SecurityContextHolder.getContext().authentication.name
    val user = userRepo.findOneByUsername(username);
    if (user != null) {
      val cnt = cntRepo.findById(user.id).orElse(UserCounter(user.id))
      cnt.counter++
      cntRepo.save(cnt)
    }
    return ModelAndView("hello", Collections.singletonMap("val", Hello(username).toString()))
  }

  @GetMapping("/counter")
  fun counters(): ModelAndView {
    val model = cntRepo.findAll().map({ it })
    return ModelAndView("hellocounter", Collections.singletonMap("counters", model))
  }
}

@Component("Hello")
class Hello (name: String = "stranger") {
  val content: String = "Hello, $name!"
  override fun toString(): String = content
}

