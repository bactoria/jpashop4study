package com.example.jpashop.domain

import com.example.jpashop.domain.delivery.Address
import javax.persistence.*

@Entity
class Member(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	var id: Long? = null,

	var name: String,

	@Embedded
	var address: Address,

	@OneToMany(mappedBy = "member")
	val orders: MutableList<Order> = mutableListOf()
)
