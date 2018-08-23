package ru.tasker.example.model

import javax.persistence.*

@Entity
@Table(name = "role")
class Role(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int,
           val name: String) {
}