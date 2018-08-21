package ru.tasker.example.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import ru.tasker.example.dao.UserRepository
import ru.tasker.example.model.User
import javax.validation.Valid

@RestController()
class SecurityController(@Autowired val urep: UserRepository) {
  @GetMapping("/","/login")
  fun login(model: Model, @RequestParam(value = "user") user: String?): ModelAndView {
    model.addAttribute("login", user)
    return ModelAndView("login", model.asMap())
  }

  @PostMapping("/login")
  fun loguser(@ModelAttribute("Credentials") crd: Credentials): String {
    return "hello, ${crd.login}"
  }

  @GetMapping("/logout")
  fun logout() = "Мы будем ждеть =)"

  @GetMapping("/register")
  fun regForm() = ModelAndView("register")

  @PostMapping("/register")
  fun register(@Valid @ModelAttribute("user") user: User?, bindingResult: BindingResult?): String {
    if (urep.findOneByUsername(user?.username) != null)
      return "Уже есть пользователь ${user?.name}"
    urep.save(user);
    return "Добро пожаловать, ${user?.name}"
  }

  @GetMapping("/userlist")
  fun listUsers(): String {
    val s = StringBuilder()
    urep.findAll().forEach({s.append(it).append("<br/>")})
    return s.toString()
  }
}

@Component("Credentials")
data class Credentials(val login: String = "", val password: String = "")

