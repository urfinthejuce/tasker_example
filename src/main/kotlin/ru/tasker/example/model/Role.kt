package ru.tasker.example.model

import javax.persistence.*

@Entity
@Table(name = "role")
class Role(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int? = null,
           val name: String? = null) {
}