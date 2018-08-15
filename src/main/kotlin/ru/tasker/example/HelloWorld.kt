package ru.tasker.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import kotlin.collections.HashMap

@Component("Hello")
class Hello (name: String = "stranger") {
  val content: String = "Hello, $name!"
  override fun toString(): String = content
}

@RestController()
class HelloController(@Autowired val repo: CounterRepo) {
  @GetMapping("/hello")
  fun hello(@RequestParam(value = "name", defaultValue = "stranger") name: String): Hello {
    val cnt = repo.findById(name).orElse(UserCounter(name))
    cnt.counter++
    repo.save(cnt)
    return Hello(name)
  }

  @GetMapping("/")
  fun index(): ModelAndView {
    val map = HashMap<String, Any>()
    map.put("Привет", "Медвед")
    map.put("Hello", Hello())
    return ModelAndView("index", map)
  }

  @GetMapping("/counters")
  fun counters(): ModelAndView {
    val model= repo.findAll().map( {it})
    return ModelAndView("counters", Collections.singletonMap("counters", model))
  }
}

interface CounterRepo: CrudRepository<UserCounter, String>

@Entity
@Table(name = "COUNTER")
class UserCounter(_name: String?) {
  @Id @Column(name = "THE_NAME", length = 30)
  val name: String? = _name
  @Column(name = "CCCCOUNTER")
  var counter: Int = 0
  constructor(): this(null)
}

