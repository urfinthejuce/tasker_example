package ru.tasker.example.validation

import org.springframework.stereotype.Component
import org.springframework.validation.MessageCodesResolver

const val USERNAME_EMPTY = "USERNAME_EMPTY"
const val USERNAME_LENGTH_2TO16 = "USERNAME_LENGTH_2TO16"
const val USERNAME_DUPLICATE = "USERNAME_DUPLICATE"
const val PASSWORD_EMPTY = "PASSWORD_EMPTY"
const val PASSWORD_LENGTH_8TO32 = "PASSWORD_LENGTH_8TO32"
const val PASSWORD_CONFIRM = "PASSWORD_CONFIRM"

val ERRORS = mapOf(
    USERNAME_EMPTY to "Задайте имя пользователя",
    USERNAME_LENGTH_2TO16 to "Имя пользователя должно содержать от двух до шестнадцати символов",
    USERNAME_DUPLICATE to "Пользователь уже существует",
    PASSWORD_EMPTY to "Задайте пароль",
    PASSWORD_LENGTH_8TO32 to "Пароль должен содержать от восьми до тридцати двух символов",
    PASSWORD_CONFIRM to "Пароли не совпадают")

@Component
class MessageCodesResolverImpl : MessageCodesResolver {
  override fun resolveMessageCodes(errorCode: String, objectName: String): Array<String> {
    return arrayOf(ERRORS[errorCode] ?: "А хер его знает что случилось")
  }

  override fun resolveMessageCodes(errorCode: String, objectName: String, field: String, fieldType: Class<*>?): Array<String> {
    return resolveMessageCodes(errorCode, objectName)
  }
}