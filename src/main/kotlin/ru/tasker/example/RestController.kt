package ru.tasker.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.util.*
import javax.persistence.*
import javax.validation.Valid

@RestController()
class RestController(@Autowired val urep: UserRepository) {
  @GetMapping("")
  fun index() = "<html><h1 style=\"text-align: center\">Дратути!</h1></html>"

  @GetMapping("/login")
  fun login(@RequestParam(value = "user") user: String?): ModelAndView =
      ModelAndView("login", Collections.singletonMap("login", user))

  @PostMapping("/login")
  fun loguser(@ModelAttribute("Credentials") crd: Credentials): String {
    return "hello, ${crd.login}"
  }

  @GetMapping("/logout")
  fun logout() = "Ну и пошел в жопу"

  @GetMapping("/register")
  fun regForm() = ModelAndView("register")

  @PostMapping("/register")
  fun register(@Valid @ModelAttribute("user") user: User?, bindingResult: BindingResult?): String {
    if (!urep.findByLogin(user?.login).isEmpty())
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

@Entity
@Table(name = "user")
@Component("user")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null,
    val login: String? = null,
    val password: String? = null,
    val name: String? = null,
    val birthdate: String? = null) {
  override fun toString(): String {
    return "login: $login, name: $name${if (!birthdate.isNullOrBlank()) ", birthdate: $birthdate" else ""}"
  }
}

interface UserRepository : CrudRepository<User, Long> {
  fun findByLogin(login: String?): List<User>
}