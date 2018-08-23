package ru.tasker.example.validation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import ru.tasker.example.dao.UserRepository
import ru.tasker.example.model.User

@Component
class UserValidator(@Autowired val urep: UserRepository): Validator {
  override fun validate(target: Any?, errors: Errors) {
    val user = target as User
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", USERNAME_EMPTY)
    if (user.username.length < 2 || user.username.length > 16)
      errors.rejectValue("username", USERNAME_LENGTH_2TO16)
    if (urep.findOneByUsername(user.username) != null)
      errors.rejectValue("username", USERNAME_DUPLICATE)
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", PASSWORD_EMPTY);
    if (user.password.length < 2 || user.password.length > 16)
      errors.rejectValue("username", PASSWORD_LENGTH_8TO32)
    if (user.passwordConfirm != user.password)
      errors.rejectValue("passwordConfirm", PASSWORD_CONFIRM)
  }

  override fun supports(clazz: Class<*>): Boolean {
    return User::class.equals(clazz)
  }



}

