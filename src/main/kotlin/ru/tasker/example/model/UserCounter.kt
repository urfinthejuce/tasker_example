package ru.tasker.example.model

import javax.persistence.*

@Entity
@Table(name = "hello_counter")
class UserCounter(
  @Id
  @Column(name="user_id")
  val userId: Int? = null,
  var counter: Int = 0,
  @OneToOne
  @JoinColumn(name="user_id")
  val user: User? = null
)