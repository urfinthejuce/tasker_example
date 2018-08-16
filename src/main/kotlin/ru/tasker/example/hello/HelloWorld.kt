package ru.tasker.example.hello

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@RestController()
@RequestMapping("/hello")
class HelloController(@Autowired val repo: CounterRepo) {
  @GetMapping("")
  fun hello(@RequestParam(value = "name", defaultValue = "stranger") name: String): ModelAndView {
    val cnt = repo.findById(name).orElse(UserCounter(name))
    cnt.counter++
    repo.save(cnt)
    return ModelAndView("hello", Collections.singletonMap("val", Hello(name).toString()))
  }

  @GetMapping("/counter")
  fun counters(): ModelAndView {
    val model = repo.findAll().map({ it })
    return ModelAndView("hellocounter", Collections.singletonMap("counters", model))
  }
}

@Component("Hello")
class Hello (name: String = "stranger") {
  val content: String = "Hello, $name!"
  override fun toString(): String = content
}

interface CounterRepo: CrudRepository<UserCounter, String>

@Entity
@Table(name = "COUNTER")
class UserCounter(_name: String? = null) {
  @Id
  val name: String? = _name
  var counter: Int = 0
}

