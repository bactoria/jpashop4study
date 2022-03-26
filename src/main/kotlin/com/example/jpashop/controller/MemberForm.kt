package com.example.jpashop.controller

import javax.validation.constraints.NotEmpty

data class MemberForm(
	@field:NotEmpty
	val name: String ?= null,
	val city: String ?= null,
	val street: String ?= null,
	val zipcode: String ?= null
)
