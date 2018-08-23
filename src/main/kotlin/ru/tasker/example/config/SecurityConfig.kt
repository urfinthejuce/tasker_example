package ru.tasker.example.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
  override fun configure(http: HttpSecurity?) {
    http!!
        .csrf().disable()
        .authorizeRequests()
          .antMatchers("/").permitAll()
          .antMatchers("/error").permitAll()
          .antMatchers("/css/*").permitAll()
          .antMatchers("/js/*").permitAll()
          .antMatchers("/userlist*").hasRole("admin")
          .antMatchers("/register*").permitAll()
          .antMatchers("/hello*").hasAuthority("user")
          .antMatchers("/hello/").hasAuthority("user")
          .anyRequest().authenticated()
        .and()
          .formLogin().loginPage("/login").permitAll().loginProcessingUrl("/login").permitAll().defaultSuccessUrl("/")
        .and()
          .logout().permitAll()
  }

  @Bean
  override fun authenticationManagerBean(): AuthenticationManager {
    return super.authenticationManagerBean()
  }
}