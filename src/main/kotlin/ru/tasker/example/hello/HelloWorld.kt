package ru.tasker.example.hello

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import ru.tasker.example.UserRepository
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@RestController()
@RequestMapping("/hello")
class HelloController(@Autowired val cntRepo: CounterRepo, @Autowired val userRepo: UserRepository) {
  @GetMapping("")
  fun hello(@RequestParam(value = "username", defaultValue = "stranger") username: String): ModelAndView {
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

interface CounterRepo: CrudRepository<UserCounter, Int>

@Entity
@Table(name = "hello_counter")
class UserCounter(userId: Int? = null) {
  @Id
  @Column(name="user_id")
  val userId: Int? = userId
  var counter: Int = 0
}

