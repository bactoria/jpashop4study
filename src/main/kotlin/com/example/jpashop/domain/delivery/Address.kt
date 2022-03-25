package com.example.jpashop.domain.delivery

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Address(
	@Column // FIXME: 없앨 수 없나..
	val city: String,
	val street: String,
	val zipcode: String
)
