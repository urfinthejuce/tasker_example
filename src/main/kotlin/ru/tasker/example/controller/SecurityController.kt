package ru.tasker.example.controller

import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import ru.tasker.example.dao.RoleRepository
import ru.tasker.example.dao.UserRepository
import ru.tasker.example.model.User
import ru.tasker.example.service.SecurityService
import ru.tasker.example.validation.UserValidator
import javax.validation.Valid

@RestController()
class SecurityController(val ctx: ApplicationContext,
                         val urep: UserRepository,
                         rolerep: RoleRepository,
                         val secService: SecurityService,
                         val uvld: UserValidator) {
  val user_Role = rolerep.findOneByName("user")
  @GetMapping("/")
  fun index() = secService.currentUser()

  @GetMapping("/login")
  fun login(model: Model, error: String?, logout: String?): ModelAndView {
    model.addAttribute("error", error)
    return ModelAndView("login", model.asMap())
  }

  @PostMapping("/login")
  fun loguser(model: Model, @ModelAttribute("Credentials") crd: Credentials): ModelAndView {
    secService.login(crd.login, crd.password)
    model.addAttribute("login", crd.login)
    return ModelAndView("login", model.asMap())
  }

  @GetMapping("/register")
  fun regForm() = ModelAndView("register")

  @PostMapping("/register")
  fun register(@Valid @ModelAttribute("user") user: User, bindingResult: BindingResult): ModelAndView {
    uvld.validate(user, bindingResult)
    if (bindingResult.hasErrors())
      return ModelAndView("register", bindingResult.fieldErrors.associate { it.field to it.defaultMessage })
    val penc: PasswordEncoder = ctx.getBean(PasswordEncoder::class)
    user.password = penc.encode(user.password)
    user.roles = setOf(user_Role)
    urep.save(user);
    secService.login(user.username, user.password)
    return ModelAndView("redirect:/hello")
  }

  @GetMapping("/userlist")
  fun listUsers(): String {
    val s = StringBuilder()
    urep.findAll().forEach({ s.append(it).append("<br/>") })
    return s.toString()
  }

  @GetMapping("/errors")
  fun error(): String {
    return "Что-то пошло не так"
  }
}

@Component("Credentials")
data class Credentials(val login: String = "", val password: String = "")

