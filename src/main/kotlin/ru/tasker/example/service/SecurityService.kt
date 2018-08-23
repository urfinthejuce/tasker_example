package ru.tasker.example.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.tasker.example.dao.UserRepository

@Service
class SecurityService(val userRepo: UserRepository, val authManager: AuthenticationManager) : UserDetailsService {
  @Transactional(readOnly = true)
  override fun loadUserByUsername(username: String): UserDetails {
    val user = userRepo.findOneByUsername(username)
    if (user != null)
      return User(user.username, user.password, user.roles?.map { SimpleGrantedAuthority(it?.name) })
    throw Exception("Пользователь $username не найден")
  }

  fun login(username: String, password: String) {
    val ud = loadUserByUsername(username)
    val token = UsernamePasswordAuthenticationToken(ud.username, ud.password, ud.authorities)
    authManager.authenticate(token)
    if (token.isAuthenticated())
      SecurityContextHolder.getContext().authentication = token
  }

  fun currentUser() = SecurityContextHolder.getContext().authentication

}
