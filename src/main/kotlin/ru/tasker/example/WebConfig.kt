package ru.tasker.example

import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader
import org.springframework.boot.web.servlet.view.MustacheView
import org.springframework.boot.web.servlet.view.MustacheViewResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.view.InternalResourceViewResolver

@Configuration
class WebConfig : WebMvcConfigurer {

}