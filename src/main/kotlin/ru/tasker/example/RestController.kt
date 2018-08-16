package ru.tasker.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import ru.tasker.example.hello.CounterRepo
import java.util.*

@RestController()
class RestController(@Autowired val repo: CounterRepo) {
  @GetMapping("")
  fun index() = "<html><h1 style=\"text-align: center\">Дратути!</h1></html>"

  @GetMapping("/login")
  fun login(@RequestParam(value = "user") user: String?): ModelAndView? = ModelAndView("login", Collections.singletonMap("login", user))

  @PostMapping("/login")
  fun loguser(@ModelAttribute("Credentials") crd: Credentials): String {
    return "hello, ${crd.login}"
  }

  @GetMapping("/logout")
  fun logout() = "Ну и пошел в жопу"

}

@Component("Credentials")
data class Credentials(val login: String="", val password: String="")